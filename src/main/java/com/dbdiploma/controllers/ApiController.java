package com.dbdiploma.controllers;

import com.dbdiploma.entities.DataSetDTO;
import com.dbdiploma.entities.Hardware;
import com.dbdiploma.entities.Software;
import com.dbdiploma.services.EventService;
import com.dbdiploma.services.HardwareService;
import com.dbdiploma.services.HostService;
import com.dbdiploma.services.SoftwareService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

    private final EventService eventService;
    private final HardwareService hardwareService;
    private final SoftwareService softwareService;
    private final HostService hostService;

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getEventsCount(){
        return eventService.getEventsCount();
    }
    @RequestMapping(value = "/hardware", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getHardwareCount(){
        return hardwareService.getHardwareCount();
    }
    @RequestMapping(value = "/software", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getSoftwareCount(){
        return softwareService.getSoftwareCount();
    }

    @RequestMapping(value = "/hosts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DataSetDTO> getHosts(){

        return hostService.getAllHosts().stream()
                .map(host -> {
                    long hardwareCount = host.getHardwareList().size();
                    long softwareCount = host.getSoftwareList().size();
                    long eventCount =
                            host.getHardwareList().stream().map(hardware -> hardware.getErrorEvents().size()).reduce(0, Integer::sum) +
                                    host.getSoftwareList().stream().map(software -> software.getErrorEvents().size()).reduce(0, Integer::sum) ;
                    return new DataSetDTO(host.getHostName(), Arrays.asList(0L, hardwareCount, softwareCount, eventCount, 0L));
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/yAxesMax", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public int getYAxesMax(){

        return hostService.getAllHosts().stream()
                .map(host -> {
                    int hardwareCount = host.getHardwareList().size();
                    int softwareCount = host.getSoftwareList().size();
                    int eventCount = host.getHardwareList().stream().map(hardware -> hardware.getErrorEvents().size()).reduce(0, Integer::sum) +
                            host.getSoftwareList().stream().map(software -> software.getErrorEvents().size()).reduce(0, Integer::sum);
                    return IntStream.of(hardwareCount, softwareCount, eventCount).max().getAsInt();
                }).max(Integer::compare).orElse(0);
    }

}
