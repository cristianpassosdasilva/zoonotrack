package com.zoonotrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zoonotrack.model.Morador;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
}
