package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IAcesso extends IBase<Acesso, UUID, AcessoDTO, AcessoOutputDTO> {
    List<AcessoOutputDTO> findAccessByFilial(UUID empresaId);
    List<UsuarioOutputDTO> findAllUsersByFilial(UUID filialId);

    Acesso findAccessByUserAndFilial(Usuario usuario, Filial filial);

    AcessoOutputDTO createFilial(AcessoDTO dto, UUID filialId);
    AcessoOutputDTO createEmpresa(AcessoDTO dto, Long empresaId);
}
