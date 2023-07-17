package com.example.news.service.common;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @param <X> X - data transfer object
 * @param <Y> Y - entity
 */
public interface GenericMapper<X,Y> {

    default List<X> mapEntitiesToDTOs(List<? extends Y> values) {
        return values.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    X mapEntityToDTO(Y value);

    default List<Y> mapDTOsToEntities(List<? extends X> values) {
        return values.stream()
                .map(this::mapDTOtoEntity)
                .collect(Collectors.toList());
    }

    Y mapDTOtoEntity(X value);

}
