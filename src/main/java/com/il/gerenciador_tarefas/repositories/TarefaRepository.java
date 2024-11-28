package com.il.gerenciador_tarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.il.gerenciador_tarefas.models.Tarefa;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>{
    Optional<Tarefa> findById(int id_tarefa);

}
