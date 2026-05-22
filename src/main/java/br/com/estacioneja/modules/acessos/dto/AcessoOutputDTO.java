package br.com.estacioneja.modules.acessos.dto;

import java.util.UUID;

import br.com.estacioneja.modules.empresa.dto.EmpresaOutputDTO;
import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.shared.enums.TipoAcesso;

public record AcessoOutputDTO(UUID id, TipoAcesso tipoAcesso, ReadUsuarioDto usuario, EmpresaOutputDTO empresa) {
    
}
