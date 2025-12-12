package com.zoonotrack.service;

import com.zoonotrack.model.Morador;
import com.zoonotrack.repository.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository repository;

    public List<Morador> listarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Morador salvar(Morador m) {
        return repository.save(m);
    }

    public Morador buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Morador não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public long total() {
        return repository.count();
    }
}
