package com.il.gerenciador_tarefas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PagesController {
    @GetMapping("/index")
    public String index() {
        return "index"; 
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro"; 
    }

    @GetMapping("/tarefas")
    public String tarefas() {
        return "tarefas"; 
    }
}
