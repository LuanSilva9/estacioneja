package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.dto.input.FilialDTO;
import br.com.estacioneja.dto.output.FilialOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IFilial extends IBase<Filial, UUID, FilialDTO, FilialOutputDTO> {
    void existsByCnpj(String cnpj);
    List<FilialOutputDTO> findAllByEmpresaId(Long empresaId);
}
