package com.il.gerenciador_tarefas.dto;

import java.time.LocalDate;

public record TarefaDTO(String titulo, String descricao, LocalDate prazo, int statusTarefa, int criador_tarefa, int executor_tarefa) {
}
