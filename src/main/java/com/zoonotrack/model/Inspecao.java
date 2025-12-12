package com.zoonotrack.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Inspecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusInspecao status = StatusInspecao.AGENDADA;

    @ManyToOne
    private Morador morador;

    @ManyToOne
    private AgenteSaude agente;

    @Column(length = 1000)
    private String observacoesGerais;

    @OneToMany(mappedBy = "inspecao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InspecaoItem> itens = new ArrayList<>();

    public int getTotalRisco() {
        return itens.stream()
                .mapToInt(InspecaoItem::getNivelRisco)
                .sum();
    }
}
