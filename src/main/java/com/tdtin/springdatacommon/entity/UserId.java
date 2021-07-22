package com.tdtin.springdatacommon.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Embeddable
public class UserId implements Serializable {
    private String identifier;
    private String tenantId;

    /**
     * constructor
     */
    public UserId() {
    }

    /**
     * Constructor
     *
     * @param tenantId tenant id
     */
    public UserId(String tenantId) {
        this.tenantId = tenantId;
        this.identifier = UUID.randomUUID().toString();
    }
}
