package com.tdtin.springdatacommon.mapping;


import java.util.List;
import com.tdtin.springdatacommon.dto.AddressDto;
import com.tdtin.springdatacommon.dto.UserDto;
import com.tdtin.springdatacommon.entity.Address;
import com.tdtin.springdatacommon.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * From {@link UserDto} tp {@link User}
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    User toUser(UserDto userDto);

    /**
     * from list of {@link AddressDto} to list of {@link Address}
     * @param addressDtos list of address dto
     * @return list of address
     */
    List<Address> toAddresses(List<AddressDto> addressDtos);
}
