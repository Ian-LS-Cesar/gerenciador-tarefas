package com.il.gerenciador_tarefas.controllers;

import com.il.gerenciador_tarefas.models.Usuario;
import com.il.gerenciador_tarefas.models.Tarefa;
import com.il.gerenciador_tarefas.dto.TarefaDTO;
import com.il.gerenciador_tarefas.dto.TarefaResponseDTO;
import com.il.gerenciador_tarefas.repositories.StatusTarefaRepository;
import com.il.gerenciador_tarefas.repositories.TarefaRepository;
import com.il.gerenciador_tarefas.repositories.UsuarioRepository;
import com.il.gerenciador_tarefas.models.StatusTarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {
    
    private final StatusTarefaRepository statusTarefaRepository;
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/criar")
    public ResponseEntity criarTarefa(@RequestBody TarefaDTO tarefaDTO) {
        Optional<StatusTarefa> status = statusTarefaRepository.findById(1);
        Optional<Usuario> criador = usuarioRepository.findById(1);
        Optional<Usuario> executor = usuarioRepository.findById(1);
        
        if(!status.isPresent() || !criador.isPresent() || !executor.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        StatusTarefa statusTarefa = status.get();
        Usuario criadorTarefa = criador.get();
        Usuario executorTarefa = executor.get();

        Tarefa novaTarefa = new Tarefa();
        
        novaTarefa.setTitulo(tarefaDTO.titulo());
        novaTarefa.setDescricao(tarefaDTO.descricao());
        novaTarefa.setStatusTarefa(statusTarefa);
        novaTarefa.setPrazo(tarefaDTO.prazo());
        novaTarefa.setCriadorTarefa(criadorTarefa);
        novaTarefa.setExecutorTarefa(executorTarefa);

        this.tarefaRepository.save(novaTarefa);

        return ResponseEntity.ok(new TarefaResponseDTO(novaTarefa.getTitulo(),novaTarefa.getDescricao()));

    }
}
