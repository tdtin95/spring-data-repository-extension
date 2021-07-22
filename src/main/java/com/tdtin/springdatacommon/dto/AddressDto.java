package com.tdtin.springdatacommon.dto;

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
public class AddressDto {
    private String street;

    private String houseNumber;

    private String supplementaryLine;

    private String zipCode;

    private String city;

    private String state;

    private String countryCode;
}
