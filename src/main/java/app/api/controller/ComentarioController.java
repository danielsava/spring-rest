package app.api.controller;

import app.api.model.ComentarioIncluirModel;
import app.api.model.ComentarioModel;
import app.domain.model.Comentario;
import app.domain.model.OrdemServico;
import app.domain.repository.OrdemServicoRepository;
import app.exception.EntidadeNotFoundException;
import app.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService ordemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioIncluirModel comentarioIncluirModel) {
        Comentario comentario = ordemServicoService.adicionarComentario(ordemServicoId, comentarioIncluirModel.getDescricao());
        return toModel(comentario);
    }

    @GetMapping
    public List<ComentarioModel> buscar(@PathVariable Long ordemServicoId) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNotFoundException("Ordem de Serviço não encontrada"));
        return toModel(ordemServico.getComentarios());
    }

    private ComentarioModel toModel(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioModel.class);
    }

    private List<ComentarioModel> toModel(List<Comentario> comentarios) {
        return comentarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
