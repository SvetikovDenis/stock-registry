package com.svetikov.stockregistry.mapper;

import com.svetikov.stockregistry.dto.AbstractDTO;
import com.svetikov.stockregistry.model.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDTO> implements Mapper<E, D> {

    @Autowired
    ModelMapper mapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {

        return dto == null
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return entity == null
                ? null
                : mapper.map(entity, dtoClass);
    }

    @Override
    public void updateEntity(D dto, E entity) {
        if (dto != null && entity != null) {
            mapper.map(dto, entity);
        }
    }

    Converter<E, D> toDtoConverter() {
        return new Converter<E, D>() {
            @Override
            public D convert(MappingContext<E, D> context) {
                E source = context.getSource();
                D destination = context.getDestination();
                mapSpecificFields(source, destination);
                return context.getDestination();
            }
        };
    }

    Converter<D, E> toEntityConverter() {

        return new Converter<D, E>() {
            @Override
            public E convert(MappingContext<D, E> context) {
                D source = context.getSource();
                E destination = context.getDestination();
                mapSpecificFields(source, destination);
                return context.getDestination();
            }
        };
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }
}
