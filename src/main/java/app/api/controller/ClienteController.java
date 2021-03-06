package app.api.controller;


import app.domain.model.Cliente;
import app.domain.repository.ClienteRepository;
import app.service.CadastroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    //@PersistenceContext
    //private EntityManager manager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService clienteService;


    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
        //return manager.createQuery("from Cliente", Cliente.class).getResultList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        Optional<Cliente> c = clienteRepository.findById(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@Valid @RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {

        if(!clienteRepository.existsById(id))
            return ResponseEntity.noContent().build();

        cliente.setId(id);
        cliente = clienteService.salvar(cliente);
        return ResponseEntity.ok().body(cliente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if(!clienteRepository.existsById(id))
            return ResponseEntity.noContent().build();
        clienteService.excluir(id);
        return ResponseEntity.ok().build();
    }

}
