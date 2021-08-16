package com.dbdiploma.repositories;

import com.dbdiploma.entities.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository
        extends JpaRepository<Host, Long> {
    Optional<Host> getHostByUuid(String uuid);
    void deleteHostByUuid(String uuid);
}
