package com.svetikov.stockregistry.mapper;

import com.svetikov.stockregistry.dto.AbstractDTO;
import com.svetikov.stockregistry.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDTO> {

    E toEntity(D dto);

    D toDto(E entity);

    void updateEntity(D dto, E entity);
}
