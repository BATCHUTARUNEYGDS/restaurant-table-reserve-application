package com.reserve.restaurantservice.mapper;


import com.reserve.restaurantservice.dto.RestaurantManagerDto;
import com.reserve.restaurantservice.entities.RestaurantManager;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantManagerMapper {

    RestaurantManagerMapper INSTANCE = Mappers.getMapper(RestaurantManagerMapper.class);

    List<RestaurantManagerDto> entityToDtos(List<RestaurantManager> restaurantManager);

    RestaurantManagerDto entityToDto(RestaurantManager restaurantManager);

    @InheritInverseConfiguration
    RestaurantManager dtoToEntity(RestaurantManagerDto restaurantManagerDto);

}
