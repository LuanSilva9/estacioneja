package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Acesso.Actor;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.update.AcessoUpdateDto;

public interface IAcesso {
    List<Usuario> findAllUsersByEmpresa(UUID empresaId);

    AcessoOutputDTO findAccessByUserAndEmpresaId(Usuario usuario, UUID empresaId);
    List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId);
    public List<AcessoOutputDTO> findAccessByUser(Usuario usuario);

    /* CRUD */
    AcessoOutputDTO create(Actor actor, AcessoDTO dto, UUID empresaId);
    void update(UUID id, AcessoUpdateDto dto);
    void delete(UUID id);

    /* Consultas */
    Acesso findEntityById(UUID id);
    AcessoOutputDTO findById(UUID id);
}
