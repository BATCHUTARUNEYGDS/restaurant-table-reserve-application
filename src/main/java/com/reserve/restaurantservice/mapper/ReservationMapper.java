package com.reserve.restaurantservice.mapper;

import com.reserve.restaurantservice.dto.ReservationDto;
import com.reserve.restaurantservice.entities.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    List<ReservationDto> entityToDtos(List<Reservation> reservations);

    ReservationDto entityToDto(Reservation reservation);

    @InheritInverseConfiguration
    Reservation dtoToEntity(ReservationDto reservationDto);
}
