package app.api.controller;

import app.api.model.OrdemServicoIncluirModel;
import app.api.model.OrdemServicoModel;
import app.domain.model.OrdemServico;
import app.domain.repository.OrdemServicoRepository;
import app.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService service;

    @Autowired
    private OrdemServicoRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoIncluirModel ordemServicoModel) {
        OrdemServico ordemServico = toEntity(ordemServicoModel);
        return toModel(service.criar(ordemServico));
    }

    @GetMapping
    public List<OrdemServicoModel> listar() {
        return toModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id) {
        Optional<OrdemServico> ordemServico = repository.findById(id);
        if(ordemServico.isPresent()) {
            OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
            return ResponseEntity.ok(ordemServicoModel);
        }
        return ResponseEntity.notFound().build();
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toModel(List<OrdemServico> ordemServicos) {
        return ordemServicos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }


    private OrdemServico toEntity(OrdemServicoIncluirModel ordemServicoIncluirModel) {
        return modelMapper.map(ordemServicoIncluirModel, OrdemServico.class);
    }

}
