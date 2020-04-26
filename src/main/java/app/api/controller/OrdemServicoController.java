package app.api.controller;

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
    public OrdemServico criar(@Valid @RequestBody OrdemServico ordemServico) {
        return service.criar(ordemServico);
    }

    @GetMapping
    public List<OrdemServico> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id) {
        Optional<OrdemServico> ordemServico = repository.findById(id);
        if(ordemServico.isPresent()) {
            OrdemServicoModel ordemServicoModel = modelMapper.map(ordemServico.get(), OrdemServicoModel.class);
            return ResponseEntity.ok(ordemServicoModel);
        }
        return ResponseEntity.notFound().build();
    }

}
