package com.zoonotrack.controller;

import com.zoonotrack.model.Morador;
import com.zoonotrack.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/moradores")
public class MoradorController {

    @Autowired
    private MoradorService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.listarTodos());
        return "moradores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("morador", new Morador());
        return "moradores/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Morador morador) {
        service.salvar(morador);
        return "redirect:/moradores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("morador", service.buscarPorId(id));
        return "moradores/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/moradores";
    }
}
