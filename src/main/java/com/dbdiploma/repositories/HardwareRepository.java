package com.dbdiploma.repositories;

import com.dbdiploma.entities.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HardwareRepository
        extends JpaRepository<Hardware, Long> {
    Optional<Hardware>
            findHardwareByUuid(String uuid);
}
