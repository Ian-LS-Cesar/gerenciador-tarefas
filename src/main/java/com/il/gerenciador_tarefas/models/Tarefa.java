package com.il.gerenciador_tarefas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Integer id_tarefa;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "prazo")
    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "status_tarefa", referencedColumnName = "id_status")
    private StatusTarefa status_tarefa;

    @ManyToOne
    @JoinColumn(name = "criador_tarefa", referencedColumnName = "id_usuario")
    private Usuario criador_tarefa;

    @ManyToOne
    @JoinColumn(name = "executor_tarefa", referencedColumnName = "id_usuario")
    private Usuario executor_tarefa;

}