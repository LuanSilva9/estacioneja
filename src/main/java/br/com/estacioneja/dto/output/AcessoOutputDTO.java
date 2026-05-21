package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.shared.enums.TipoAcesso;

public record AcessoOutputDTO(UUID id, TipoAcesso tipoAcesso, ReadUsuarioDto usuario, EmpresaOutputDTO empresa) {
    
}
