package com.dbdiploma.entities;

import lombok.Data;

@Data
public class InfoDTO {

    private long hostCount;
    private long hardwareCount;
    private long softwareCount;
    private long errorEventsCount;

    public static InfoDTO create(long hostCount,
                         long hardwareCount,
                         long softwareCount,
                         long errorEventsCount){

        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setHostCount(hostCount);
        infoDTO.setHardwareCount(hardwareCount);
        infoDTO.setSoftwareCount(softwareCount);
        infoDTO.setErrorEventsCount(errorEventsCount);

        return infoDTO;
    }
}
