package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IAcesso extends IBase<Acesso, UUID, AcessoDTO, AcessoOutputDTO> {
    List<AcessoOutputDTO> findAccessByCompany(Long empresaId);
    Acesso findAccessByUserAndCompany(Usuario usuario, Empresa empresa);
}
