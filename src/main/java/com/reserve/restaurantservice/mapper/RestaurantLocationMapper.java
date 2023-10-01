package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RestaurantLocationDto;
import com.reserve.restaurantservice.entities.RestaurantLocation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantLocationMapper {

    RestaurantLocationMapper INSTANCE = Mappers.getMapper(RestaurantLocationMapper.class);

    List<RestaurantLocationDto> entityToDtos(List<RestaurantLocation> restaurantLocations);

    RestaurantLocationDto entityToDto(RestaurantLocation restaurantLocation);

    @InheritInverseConfiguration
    RestaurantLocation dtoToEntity(RestaurantLocationDto restaurantLocationDto);

}
