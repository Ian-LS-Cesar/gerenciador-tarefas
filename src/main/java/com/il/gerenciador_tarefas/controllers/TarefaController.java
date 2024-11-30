package com.il.gerenciador_tarefas.controllers;

import com.il.gerenciador_tarefas.models.Usuario;
import com.il.gerenciador_tarefas.models.Tarefa;
import com.il.gerenciador_tarefas.dto.FeedbackDTO;
import com.il.gerenciador_tarefas.dto.TarefaDTO;
import com.il.gerenciador_tarefas.dto.TarefaResponseDTO;
import com.il.gerenciador_tarefas.repositories.StatusTarefaRepository;
import com.il.gerenciador_tarefas.repositories.TarefaRepository;
import com.il.gerenciador_tarefas.repositories.UsuarioRepository;
import com.il.gerenciador_tarefas.models.StatusTarefa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

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
        novaTarefa.setStatus_tarefa(statusTarefa);
        novaTarefa.setPrazo(tarefaDTO.prazo());
        novaTarefa.setCriador_tarefa(criadorTarefa);
        novaTarefa.setExecutor_tarefa(executorTarefa);

        this.tarefaRepository.save(novaTarefa);

        return ResponseEntity.ok(new TarefaResponseDTO(novaTarefa.getId_tarefa(), novaTarefa.getTitulo(),novaTarefa.getDescricao(), novaTarefa.getPrazo(), novaTarefa.getStatus_tarefa().getId_status()));

    }

    @GetMapping("/listar")
    public ResponseEntity<Map<String, List<TarefaResponseDTO>>> listarTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        
        
        Map<String, List<TarefaResponseDTO>> tarefasPorStatus = new HashMap<>();
        
        for (Tarefa tarefa : tarefas) {
            String descricaoStatus = tarefa.getStatus_tarefa().getDescricaoStatusTarefa();
            
            tarefasPorStatus.putIfAbsent(descricaoStatus, new ArrayList<>());
            
            tarefasPorStatus.get(descricaoStatus).add(new TarefaResponseDTO(tarefa.getId_tarefa() ,tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getPrazo(), tarefa.getStatus_tarefa().getId_status()));
        }
        
        return ResponseEntity.ok(tarefasPorStatus);
    }

    @PostMapping("/status")
    public ResponseEntity<Void> feedbackTarefa(@RequestBody FeedbackDTO feedbackDTO) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(feedbackDTO.id_tarefa());

        if (!tarefaOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Tarefa tarefa = tarefaOptional.get();

        Optional<StatusTarefa> novoStatus = statusTarefaRepository.findById(feedbackDTO.novo_status_tarefa());
        if (!novoStatus.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        tarefa.setStatus_tarefa(novoStatus.get());
        tarefaRepository.save(tarefa);

        return ResponseEntity.ok().build();
    }

}
