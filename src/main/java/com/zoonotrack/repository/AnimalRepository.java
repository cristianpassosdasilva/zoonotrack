package com.zoonotrack.repository;

import com.zoonotrack.model.Animal;
import com.zoonotrack.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByMorador(Morador morador);
}
