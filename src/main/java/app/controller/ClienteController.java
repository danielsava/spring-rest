package app.controller;


import app.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClienteController {

    @GetMapping("/clientes")
    public List<Cliente> listar() {

        Cliente c1 = new Cliente();
        c1.setId(1L);
        c1.setNome("Tiburcio Albuquerque Jacira");
        c1.setEmail("joao@email.com");
        c1.setTelefone("62 99981-6300");

        Cliente c2 = new Cliente();
        c2.setId(2L);
        c2.setNome("Asdrubal");
        c2.setEmail("Asdrubal@email.com");
        c2.setTelefone("62 99953-6300");

        return Arrays.asList(c1, c2);
    }

}
