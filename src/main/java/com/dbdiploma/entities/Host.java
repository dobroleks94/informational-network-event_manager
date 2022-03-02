package com.dbdiploma.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String hostName;
    private String ipAddress;
    @CreationTimestamp
    private LocalDate registrationDate;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hardware> hardwareList;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Software> softwareList;

    public Host(){
        this.uuid = UUID.randomUUID().toString();
    }

    public List<String> userEmails(){
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }

    public void setHardwareList(List<Hardware> hardwareList) {
        this.hardwareList = hardwareList;
        hardwareList.forEach(hardware -> hardware.setOwnerHost(this));
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
        softwareList.forEach(software -> software.setOwnerHost(this));
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", hostName='" + hostName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }

}
