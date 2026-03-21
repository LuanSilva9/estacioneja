package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
public interface IEmpresa {
    
    /* CRUD */
    EmpresaOutputDTO create(EmpresaDTO dto);
    void update(UUID id, EmpresaUpdateDto dto);
    void delete(UUID id);
    
    /* Consultas */
    Empresa findEntityById(UUID id);
    EmpresaOutputDTO findById(UUID id);
    List<EmpresaOutputDTO> findAll();
}
