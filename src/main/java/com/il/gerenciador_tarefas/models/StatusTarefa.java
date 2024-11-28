package com.il.gerenciador_tarefas.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "statusTarefas")
public class StatusTarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer id;

    @Column(name = "descricao_status_tarefa")
    private String descricaoStatusTarefa;

}