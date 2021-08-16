package com.dbdiploma.services;

import com.dbdiploma.entities.ErrorEvent;
import com.dbdiploma.entities.Hardware;
import com.dbdiploma.entities.Host;
import com.dbdiploma.entities.Software;
import com.dbdiploma.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Host updateHostSoftwareForEvent(ErrorEvent errorEvent, String softwareUUID, Host host){

        errorEvent.setHardware(null);
        Software updated = host.getSoftwareList().stream()
                .filter(software -> software.getUuid().equals(softwareUUID))
                .findFirst().get();
        host.getSoftwareList().removeIf(software -> software.getUuid().equals(softwareUUID));
        if(updated.getErrorEvents() == null)
            updated.setErrorEvents(new ArrayList<>());
        if(host.getHardwareList() == null)
            host.setHardwareList(new ArrayList<>());
        updated.getErrorEvents().add(errorEvent);
        host.getSoftwareList().add(updated);
        errorEvent.setSoftware(updated);

        return host;
    }
    public Host updateHostHardwareForEvent(ErrorEvent errorEvent, String hardwareUUID, Host host){

        errorEvent.setSoftware(null);
        Hardware updated = host.getHardwareList().stream()
                .filter(hardware -> hardware.getUuid().equals(hardwareUUID))
                .findFirst().get();
        host.getHardwareList().removeIf(hardware -> hardware.getUuid().equals(hardwareUUID));
        if(updated.getErrorEvents() == null)
            updated.setErrorEvents(new ArrayList<>());
        if(host.getHardwareList() == null)
            host.setHardwareList(new ArrayList<>());
        updated.getErrorEvents().add(errorEvent);
        host.getHardwareList().add(updated);
        errorEvent.setHardware(updated);

        return host;
    }
    public long getEventsCount() {
        return eventRepository.count();
    }

    public ErrorEvent save(ErrorEvent errorEvent) {
        return eventRepository.save(errorEvent);
    }

    public List<ErrorEvent> getAllEvents() {
        return eventRepository.findAll();
    }
}
