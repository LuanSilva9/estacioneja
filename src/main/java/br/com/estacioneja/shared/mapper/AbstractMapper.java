package br.com.estacioneja.shared.mapper;

import java.util.List;

public abstract class AbstractMapper<E, D> {

    public abstract D toDto(E entity);

    public List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).toList();
    }
}
