package br.com.estacioneja.usecases.interfaces;

import br.com.estacioneja.domain.model.Acesso.Acesso;

public interface IAcesso {
    Acesso getById(Long id);
}
