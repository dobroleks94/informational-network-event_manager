package com.dbdiploma.repositories;

import com.dbdiploma.entities.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository
        extends JpaRepository<Software, Long> {
    Optional<Software>
            findSoftwareByUuid(String uuid);
}
