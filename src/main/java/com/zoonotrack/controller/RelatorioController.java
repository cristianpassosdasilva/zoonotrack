package com.zoonotrack.controller;

import com.zoonotrack.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("moradoresTop", relatorioService.topMoradoresPorAnimais(5));
        model.addAttribute("agentesTop", relatorioService.agentesComMaisInspecoes(5));
        model.addAttribute("inspecoesRisco", relatorioService.inspecoesComMaiorRisco(5));
        return "relatorios/index";
    }
}
