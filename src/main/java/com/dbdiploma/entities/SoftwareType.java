package com.dbdiploma.entities;

import lombok.Getter;

@Getter
public enum SoftwareType {
    APPLICATION_SOFTWARE("Прикладне програмне забезпечення"),
    SYSTEM_SOFTWARE("Системне програмне забезпечення"),
    PROGRAMMING_SOFTWARE("ПЗ для розробки програмних продуктів"),
    DRIVER_SOFTWARE("Драйвери"),
    SECURITY_SOFTWARE("Безпека та Антивірусне ПЗ"),
    UTILITIES("Утиліти");

    private final String softwareName;

    SoftwareType(String softwareName) {
        this.softwareName = softwareName;
    }
}
