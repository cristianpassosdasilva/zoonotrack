package com.zoonotrack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}