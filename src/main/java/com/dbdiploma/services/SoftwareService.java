package com.dbdiploma.services;

import com.dbdiploma.entities.Software;
import com.dbdiploma.repositories.SoftwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    public Optional<Software> getSoftwareByUUID(String softwareUUID) {
        return softwareRepository.findSoftwareByUuid(softwareUUID);
    }

    public long getSoftwareCount() {
        return softwareRepository.count();
    }

    public Software save(Software software) {
        return softwareRepository.save(software);
    }

    public Software updateSoftware(Software newSftw, Software oldSftw) {

        newSftw.setId(oldSftw.getId());
        newSftw.setOwnerHost(oldSftw.getOwnerHost());
        newSftw.setErrorEvents(oldSftw.getErrorEvents());
        if (newSftw.getOwnerHost() != null)
            newSftw.getOwnerHost().setHardwareList(newSftw.getOwnerHost().getHardwareList());

        return newSftw;
    }

    public List<Software> getAllSoftware() {
        return softwareRepository.findAll();
    }

    public void deleteById(Long id) {
        softwareRepository.deleteById(id);
    }
}
