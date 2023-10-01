package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.RatingCountDto;
import com.reserve.restaurantservice.entities.RatingCount;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingCountMapper {


    RatingCountMapper INSTANCE = Mappers.getMapper(RatingCountMapper.class);

    List<RatingCountDto> entityToDtos(List<RatingCount> RatingCounts);

    RatingCountDto entityToDto(RatingCount restaurantCount);

    @InheritInverseConfiguration
    RatingCount dtoToEntity(RatingCountDto restaurantCountDto);
}
