package com.il.gerenciador_tarefas.controllers;

import com.il.gerenciador_tarefas.models.Usuario;
import com.il.gerenciador_tarefas.models.Funcao;
import com.il.gerenciador_tarefas.dto.LoginRequestDTO;
import com.il.gerenciador_tarefas.dto.RegisterRequestDTO;
import com.il.gerenciador_tarefas.dto.ResponseDTO;
import com.il.gerenciador_tarefas.infra.security.TokenService;
import com.il.gerenciador_tarefas.repositories.FuncaoRepository;
import com.il.gerenciador_tarefas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final FuncaoRepository funcaoRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Usuario usuario = this.usuarioRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/cadastro")
    public ResponseEntity cadastro(@RequestBody RegisterRequestDTO body){
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(body.email());

        if(usuario.isEmpty()) {
            Funcao funcao = funcaoRepository.findById(body.funcao())
            .orElseThrow(() -> new RuntimeException("Função não encontrada"));
            
            Usuario novoUsuario = new Usuario();
            novoUsuario.setFuncao(funcao);
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            novoUsuario.setEmail(body.email());
            novoUsuario.setNome(body.nome());
            this.usuarioRepository.save(novoUsuario);

            String token = this.tokenService.generateToken(novoUsuario);
            return ResponseEntity.ok(new ResponseDTO(novoUsuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
