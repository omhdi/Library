package com.maids.repositories;

import com.maids.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronsRepositories extends JpaRepository<Patron, Long> {
}
