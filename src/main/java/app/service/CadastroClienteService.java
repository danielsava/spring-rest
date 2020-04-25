package app.service;

import app.domain.model.Cliente;
import app.domain.repository.ClienteRepository;
import app.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente salvar(Cliente cliente)  {

        Cliente clienteJaExiste = repository.findByEmail(cliente.getEmail());

        if(clienteJaExiste != null && !clienteJaExiste.equals(cliente))
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail");

        return repository.save(cliente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
