package com.il.gerenciador_tarefas.repositories;

import com.il.gerenciador_tarefas.models.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {
    Optional<Funcao> findById(int id_funcao);
}
