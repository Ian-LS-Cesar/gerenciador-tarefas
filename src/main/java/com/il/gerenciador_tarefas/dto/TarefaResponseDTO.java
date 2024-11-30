package com.il.gerenciador_tarefas.dto;

import java.time.LocalDate;

import com.il.gerenciador_tarefas.models.StatusTarefa;

public record TarefaResponseDTO(int id_tarefa, String titulo, String descricao, LocalDate prazo, int status_tarefa) {

}
