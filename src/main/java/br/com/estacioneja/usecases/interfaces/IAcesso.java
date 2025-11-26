package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;

public interface IAcesso {
    List<Usuario> findAllUsersByEmpresa(UUID empresaId);

    Optional<Acesso> findAccessByUserAndEmpresa(Usuario usuario, Empresa empresa); 
    List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId);

    /* CRUD */
    AcessoOutputDTO create(AcessoDTO dto, UUID empresaId);
    void update(UUID id, AcessoDTO dto);
    void delete(UUID id);

    /* Consultas */
    Acesso findEntityById(UUID id);
    AcessoOutputDTO findById(UUID id);
}
