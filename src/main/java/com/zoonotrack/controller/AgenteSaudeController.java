package com.zoonotrack.controller;

import com.zoonotrack.model.AgenteSaude;
import com.zoonotrack.service.AgenteSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agentes")
public class AgenteSaudeController {

    @Autowired
    private AgenteSaudeService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.listarTodos());
        return "agentes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("agente", new AgenteSaude());
        return "agentes/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("agente", service.buscarPorId(id));
        return "agentes/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute AgenteSaude agente, RedirectAttributes redirectAttributes) {
        service.salvar(agente);
        redirectAttributes.addFlashAttribute("mensagem", "Agente salvo com sucesso!");
        return "redirect:/agentes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Agente excluído.");
        return "redirect:/agentes";
    }
}
