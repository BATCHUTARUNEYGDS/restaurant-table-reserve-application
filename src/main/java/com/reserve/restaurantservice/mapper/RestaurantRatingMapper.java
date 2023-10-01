package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RestaurantRatingDto;
import com.reserve.restaurantservice.entities.RestaurantRating;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantRatingMapper {

    RestaurantRatingMapper INSTANCE = Mappers.getMapper(RestaurantRatingMapper.class);

    List<RestaurantRatingDto> entityToDtos(List<RestaurantRating> restaurantRatings);

    RestaurantRatingDto entityToDto(RestaurantRating restaurantRating);

    @InheritInverseConfiguration
    RestaurantRating dtoToEntity(RestaurantRatingDto restaurantRatingDto);
}
