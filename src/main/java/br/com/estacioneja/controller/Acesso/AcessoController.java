package br.com.estacioneja.controller.Acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.input.TipoAcessoDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/acesso")
public class AcessoController {
    @Autowired
    private AcessoService acessoService;

    @GetMapping("get/{id}")
    public ResponseEntity<Acesso> getAccessById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(acessoService.getById(id));
    }
    
    @PostMapping("set")
    public ResponseEntity<String> setAccess(@RequestBody AcessoDTO dto) {
        try {
            this.acessoService.createAccess(dto);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Permissão " + dto.tipoAcesso() + " Concedida com successo!");
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Permissão Negada\n" + e);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> putAccess(@PathVariable Long id, @RequestBody TipoAcessoDTO tipoAcesso) throws Exception {
        try {
            this.acessoService.putAccess(id, tipoAcesso.tipoAcesso());

            return ResponseEntity.status(HttpStatus.OK).body("Acesso Mudado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Acesso não foi mudado");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteAccess(@PathVariable Long id) {
        try {
            acessoService.deleteAccess(id);

            return ResponseEntity.status(HttpStatus.OK).body("Acesso Removido");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel remover o acesso, " + e);
        }
    }
}
