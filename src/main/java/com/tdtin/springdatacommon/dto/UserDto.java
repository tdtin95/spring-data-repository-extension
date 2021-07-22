package com.tdtin.springdatacommon.dto;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;

    private String firstname;

    private String lastname;

    private OffsetDateTime validFrom;

    private List<AddressDto> address;
}
