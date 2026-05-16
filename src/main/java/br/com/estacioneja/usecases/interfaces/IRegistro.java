package br.com.estacioneja.usecases.interfaces;


import java.util.UUID;

import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
public interface IRegistro { 
    /* CRUD */
    RegistroOutputDTO create(RegistroDTO dto);

    /* Consultas */
    Registro findEntityById(UUID id);
    RegistroOutputDTO findById(UUID id);
}
