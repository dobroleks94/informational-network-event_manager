package com.dbdiploma.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String hardwareName;
    private String serialNumber;
    private Producer producer;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ErrorEvent> errorEvents;
    @ManyToOne
    private Host ownerHost;

    public Hardware(){
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Hardware{" +
                "id=" + id +
                ", hardwareName='" + hardwareName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", producer=" + producer +
                '}';
    }
}
