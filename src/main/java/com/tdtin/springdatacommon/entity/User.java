package com.tdtin.springdatacommon.entity;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NamedEntityGraph(
    name = "user-entity-graph",
    attributeNodes = {
        @NamedAttributeNode("address")
    }
)
@Table(name = "fp_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenantid", "username"})})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class User {
    @EmbeddedId
    private UserId id;

    private String username;

    private String firstname;

    private String lastname;

    private OffsetDateTime validFrom;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Address> address;
}
