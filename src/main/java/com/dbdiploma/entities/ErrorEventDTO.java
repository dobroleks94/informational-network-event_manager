package com.dbdiploma.entities;

import lombok.Data;

@Data
public class ErrorEventDTO {
    private String eventName;
    private String description;
    private EventDedication eventDedication;
    private String hardwareUUID;
    private String softwareUUID;
}
