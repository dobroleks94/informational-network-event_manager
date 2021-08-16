package com.dbdiploma.services;

import com.dbdiploma.entities.Hardware;
import com.dbdiploma.repositories.HardwareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HardwareService {

    private final HardwareRepository hardwareRepository;
    public long getHardwareCount(){
        return hardwareRepository.count();
    }

    public Optional<Hardware> getHardwareByUUID(String hardwareUUID) {
        return hardwareRepository.findHardwareByUuid(hardwareUUID);
    }

    public List<Hardware> getAllHardware() {
        return hardwareRepository.findAll();
    }
    public void delete(Hardware hardware){
        hardwareRepository.delete(hardware);
    }
    public Hardware save(Hardware hardware) {
        return hardwareRepository.save(hardware);
    }

    public Hardware prepareToDelete(Hardware hardware) {
        if (hardware.getOwnerHost() != null)
            hardware.getOwnerHost()
                    .getHardwareList()
                    .removeIf(hardw -> hardw.getUuid().equals(hardware.getUuid()));
        hardware.setOwnerHost(null);
        return hardware;
    }

    public Hardware updateHardware(Hardware newHrdw, Hardware oldHrdw){

        newHrdw.setId(oldHrdw.getId());
        newHrdw.setOwnerHost(oldHrdw.getOwnerHost());
        newHrdw.setErrorEvents(oldHrdw.getErrorEvents());
        if (newHrdw.getOwnerHost() != null)
            newHrdw.getOwnerHost().setHardwareList(oldHrdw.getOwnerHost().getHardwareList());

        return newHrdw;
    }

    public void deleteById(Long id) {
        hardwareRepository.deleteById(id);
    }
}
