package com.tdtin.springdatacommon.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String street;

    private String houseNumber;

    private String supplementaryLine;

    private String zipCode;

    private String city;

    private String state;

    private String countryCode;


    @JsonIgnore
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "user_identifier", referencedColumnName = "identifier"),
        @JoinColumn(name = "user_tenantid", referencedColumnName = "tenantid"),

    })
    private User user;

    /**
     * constructor
     */
    public Address() {
    }
}
