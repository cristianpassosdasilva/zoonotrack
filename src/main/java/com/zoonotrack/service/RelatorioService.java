package com.zoonotrack.service;

import com.zoonotrack.model.Inspecao;
import com.zoonotrack.repository.AnimalRepository;
import com.zoonotrack.repository.InspecaoRepository;
import com.zoonotrack.service.dto.AgenteResumoDTO;
import com.zoonotrack.service.dto.InspecaoRiscoDTO;
import com.zoonotrack.service.dto.MoradorResumoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RelatorioService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private InspecaoRepository inspecaoRepository;

    public List<MoradorResumoDTO> topMoradoresPorAnimais(int limite) {
        Map<String, Long> agrupado = animalRepository.findAll().stream()
                .filter(animal -> animal.getMorador() != null)
                .collect(Collectors.groupingBy(animal -> animal.getMorador().getNome(), Collectors.counting()));

        return agrupado.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limite)
                .map(entry -> new MoradorResumoDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<AgenteResumoDTO> agentesComMaisInspecoes(int limite) {
        Map<String, Long> agrupado = inspecaoRepository.findAll().stream()
                .filter(inspecao -> inspecao.getAgente() != null)
                .collect(Collectors.groupingBy(inspecao -> inspecao.getAgente().getNome(), Collectors.counting()));

        return agrupado.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limite)
                .map(entry -> new AgenteResumoDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<InspecaoRiscoDTO> inspecoesComMaiorRisco(int limite) {
        return inspecaoRepository.findAll().stream()
                .sorted(Comparator.<Inspecao>comparingInt(Inspecao::getTotalRisco).reversed())
                .limit(limite)
                .map(inspecao -> new InspecaoRiscoDTO(
                        inspecao.getId(),
                        inspecao.getMorador() != null ? inspecao.getMorador().getNome() : "Sem morador",
                        inspecao.getData(),
                        inspecao.getTotalRisco()
                ))
                .toList();
    }
}
