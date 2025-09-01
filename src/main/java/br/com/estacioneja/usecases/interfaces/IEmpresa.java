package br.com.estacioneja.usecases.interfaces;

import java.util.List;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IEmpresa extends IBase<Empresa, Long, EmpresaDTO, EmpresaOutputDTO>  {
    List<EmpresaOutputDTO> findAll();
    
    void existsByCnpj(String cnpj);
}
