package com.zoonotrack.service;

import com.zoonotrack.model.Inspecao;
import com.zoonotrack.model.InspecaoItem;
import com.zoonotrack.repository.InspecaoItemRepository;
import com.zoonotrack.repository.InspecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    @Autowired
    private InspecaoItemRepository itemRepository;

    public List<Inspecao> listarTodas() {
        return inspecaoRepository.findAll(Sort.by(Sort.Direction.DESC, "data"));
    }

    public List<Inspecao> ultimas(int limite) {
        return listarTodas().stream().limit(limite).toList();
    }

    public Inspecao prepararNova() {
        Inspecao inspecao = new Inspecao();
        inspecao.setData(LocalDate.now());
        return inspecao;
    }

    public Inspecao salvar(Inspecao inspecao) {
        return inspecaoRepository.save(inspecao);
    }

    public Inspecao buscarPorId(Long id) {
        return inspecaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inspeção não encontrada"));
    }

    @Transactional
    public void adicionarItem(Long inspecaoId, InspecaoItem item) {
        Inspecao inspecao = buscarPorId(inspecaoId);
        if (item.getAnimal() != null && item.getAnimal().getId() == null) {
            item.setAnimal(null);
        }
        item.setInspecao(inspecao);
        inspecao.getItens().add(item);
        inspecaoRepository.save(inspecao);
    }

    @Transactional
    public void removerItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public void excluir(Long id) {
        inspecaoRepository.deleteById(id);
    }

    public long total() {
        return inspecaoRepository.count();
    }
}
