package br.com.estacioneja.controller.Registro;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
import br.com.estacioneja.services.Registro.RegistroService;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/registro")
@PreAuthorize("hasRole('ADMIN')")
public class RegistroController {
    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroOutputDTO> listarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(registroService.findById(id));
    }
    

    @PostMapping
    public ResponseEntity<RegistroOutputDTO> criar(@RequestBody RegistroDTO dto) {
        RegistroOutputDTO registro = registroService.create(dto);

        URI location = URI.create(String.format("/api/v1/registro/%s", registro.id()));
        
        return ResponseEntity.created(location).body(registro);
    }
    


}
