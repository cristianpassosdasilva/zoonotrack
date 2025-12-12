package com.zoonotrack.service;

import com.zoonotrack.model.AgenteSaude;
import com.zoonotrack.repository.AgenteSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenteSaudeService {

    @Autowired
    private AgenteSaudeRepository repository;

    public List<AgenteSaude> listarTodos() {
        return repository.findAll(Sort.by("nome"));
    }

    public AgenteSaude buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agente não encontrado"));
    }

    public AgenteSaude salvar(AgenteSaude agenteSaude) {
        return repository.save(agenteSaude);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public long total() {
        return repository.count();
    }
}
