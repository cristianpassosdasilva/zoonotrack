package com.zoonotrack.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Morador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String rua;
    private String numero;
    private String bairro;
    private String cep;

    @Column(length = 500)
    private String observacaoRiscoHistorico;

    @OneToMany(mappedBy = "morador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais = new ArrayList<>();

    @OneToMany(mappedBy = "morador")
    private List<Inspecao> inspecoes = new ArrayList<>();
}
