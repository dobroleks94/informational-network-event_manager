package com.dbdiploma.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String softwareName;
    private boolean licence; //Licence activated
    private SoftwareType softwareType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ErrorEvent> errorEvents;
    @ManyToOne
    private Host ownerHost;

    public Software() {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Software{" +
                "id=" + id +
                ", softwareName='" + softwareName + '\'' +
                ", licence=" + licence +
                ", softwareType=" + softwareType +
                '}';
    }
}
