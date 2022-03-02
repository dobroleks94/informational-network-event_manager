package com.dbdiploma.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String email;
    private String password;
    private String role;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Host> hosts;

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void setHost(Host host) {
        if(hosts == null)
            this.hosts = new ArrayList<>();
        if (host.getUsers() == null)
            host.setUsers(new ArrayList<>());
        host.getUsers().add(this);
        hosts.add(host);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
