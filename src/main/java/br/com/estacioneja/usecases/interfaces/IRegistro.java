package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IRegistro extends IBase<Registro, UUID, RegistroDTO, RegistroOutputDTO> { 
    RegistroOutputDTO registrarSaida(Registro registro);
}
