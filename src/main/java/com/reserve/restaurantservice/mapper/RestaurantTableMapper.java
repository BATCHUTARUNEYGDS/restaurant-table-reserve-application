package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RestaurantTableDto;
import com.reserve.restaurantservice.entities.RestaurantTable;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {

    RestaurantTableMapper INSTANCE = Mappers.getMapper(RestaurantTableMapper.class);

    List<RestaurantTableDto> entityToDtos(List<RestaurantTable> restaurantTables);

    RestaurantTableDto entityToDto(RestaurantTable restaurantTable);

    @InheritInverseConfiguration
    RestaurantTable dtoToEntity(RestaurantTableDto restaurantTableDto);
}
