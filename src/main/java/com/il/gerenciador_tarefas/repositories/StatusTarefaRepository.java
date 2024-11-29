package com.il.gerenciador_tarefas.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.il.gerenciador_tarefas.models.StatusTarefa;

public interface StatusTarefaRepository extends JpaRepository<StatusTarefa, Integer>{
    Optional<StatusTarefa> findById(int id_status);

}
