package com.il.gerenciador_tarefas.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "funcao")
public class Funcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcao")
    private Integer id;

    @Column(name = "descricao_funcao")
    private String descricaoFuncao;

}