package br.com.estacioneja.usecases.interfaces;


import java.util.UUID;

import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
public interface IRegistro { 
    RegistroOutputDTO registrarSaida(Registro registro);

    /* CRUD */
    RegistroOutputDTO create(RegistroDTO dto);
    void update(UUID id, RegistroDTO dto);
    void delete(UUID id);

    /* Consultas */
    Registro findEntityById(UUID id);
    RegistroOutputDTO findById(UUID id);
}
