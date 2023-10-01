package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RestaurantDto;
import com.reserve.restaurantservice.entities.Restaurant;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    List<RestaurantDto> entityToDtos(List<Restaurant> restaurants);

    RestaurantDto entityToDto(Restaurant restaurant);

    @InheritInverseConfiguration
    Restaurant dtoToEntity(RestaurantDto restaurantDto);


}
