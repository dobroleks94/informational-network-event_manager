package com.dbdiploma.entities;

import com.dbdiploma.entities.enums.EventDedication;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ErrorEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private String description;
    private EventDedication eventDedication;
    @ManyToOne(cascade = CascadeType.ALL)
    private Hardware hardware;
    @ManyToOne(cascade = CascadeType.ALL)
    private Software software;

    @Override
    public String toString() {
        return "ErrorEvent{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", eventDedication=" + eventDedication +
                '}';
    }
}
