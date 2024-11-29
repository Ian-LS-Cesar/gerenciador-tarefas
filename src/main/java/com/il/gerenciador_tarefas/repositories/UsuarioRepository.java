package com.il.gerenciador_tarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.il.gerenciador_tarefas.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(int _usuario);
}
