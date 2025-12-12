package com.zoonotrack.service;

import com.zoonotrack.model.Animal;
import com.zoonotrack.model.Morador;
import com.zoonotrack.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    public List<Animal> listarTodos() {
        return repository.findAll(Sort.by("nome"));
    }

    public List<Animal> listarPorMorador(Morador morador) {
        return repository.findByMorador(morador);
    }

    public Animal salvar(Animal animal) {
        return repository.save(animal);
    }

    public Animal buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public long total() {
        return repository.count();
    }
}
