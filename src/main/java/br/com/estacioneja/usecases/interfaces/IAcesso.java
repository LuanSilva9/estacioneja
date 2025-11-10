package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IAcesso extends IBase<Acesso, UUID, AcessoDTO, AcessoOutputDTO> {
    List<AcessoOutputDTO> findAccessByFilial(UUID empresaId);
    List<Usuario> findAllUsersByFilial(UUID filialId);

    Optional<Acesso> findAccessByUserAndFilial(Usuario usuario, Filial filial);
    Optional<Acesso> findAccessByUserAndEmpresa(Usuario usuario, Empresa empresa); 

    AcessoOutputDTO createFilial(AcessoDTO dto, UUID filialId);
    AcessoOutputDTO createEmpresa(AcessoDTO dto, Long empresaId);
}
