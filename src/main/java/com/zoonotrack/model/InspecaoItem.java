package com.zoonotrack.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class InspecaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoFoco tipoFoco;

    @Column(length = 200)
    private String descricao;

    private int nivelRisco;

    @Column(length = 500)
    private String recomendacao;

    @ManyToOne
    private Animal animal;

    @ManyToOne(optional = false)
    private Inspecao inspecao;
}
