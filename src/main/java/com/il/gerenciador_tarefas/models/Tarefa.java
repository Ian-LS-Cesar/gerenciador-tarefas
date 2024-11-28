package com.il.gerenciador_tarefas.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "prazo")
    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "status_tarefa")
    private StatusTarefa statusTarefa;

    @ManyToOne
    @JoinColumn(name = "criador_tarefa")
    private Usuario criadorTarefa;

    @ManyToOne
    @JoinColumn(name = "executor_tarefa")
    private Usuario executorTarefa;

}