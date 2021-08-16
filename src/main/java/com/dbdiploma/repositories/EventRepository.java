package com.dbdiploma.repositories;

import com.dbdiploma.entities.ErrorEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<ErrorEvent, Long> {
}
