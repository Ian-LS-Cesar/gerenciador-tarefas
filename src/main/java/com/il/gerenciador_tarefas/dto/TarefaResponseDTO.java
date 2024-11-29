package com.il.gerenciador_tarefas.dto;

import java.time.LocalDate;

public record TarefaResponseDTO(String titulo, String descricao, LocalDate prazo) {

}
