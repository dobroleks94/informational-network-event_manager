package com.dbdiploma.entities;

import lombok.Getter;

@Getter
public enum EventDedication {
    HARDWARE_EVENT("Подія для Апаратного Забезпечення"), SOFTWARE_EVENT("Подія для Програмного Забезпечення");

    private final String name;
    EventDedication(String name){
        this.name = name;
    }
}
