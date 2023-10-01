package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RestaurantDto;
import com.reserve.restaurantservice.dto.RestaurantTypeDto;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantTypeMapper {

    RestaurantTypeMapper INSTANCE = Mappers.getMapper(RestaurantTypeMapper.class);

    List<RestaurantTypeDto> entityToDtos(List<RestaurantType> restaurantTypes);

    RestaurantTypeDto entityToDto(RestaurantType restaurantType);

    @InheritInverseConfiguration
    RestaurantType dtoToEntity(RestaurantTypeDto restaurantTypeDto);
}
