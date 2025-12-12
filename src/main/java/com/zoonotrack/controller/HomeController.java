package com.zoonotrack.controller;

import com.zoonotrack.service.AgenteSaudeService;
import com.zoonotrack.service.AnimalService;
import com.zoonotrack.service.InspecaoService;
import com.zoonotrack.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MoradorService moradorService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AgenteSaudeService agenteSaudeService;

    @Autowired
    private InspecaoService inspecaoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalMoradores", moradorService.total());
        model.addAttribute("totalAnimais", animalService.total());
        model.addAttribute("totalAgentes", agenteSaudeService.total());
        model.addAttribute("totalInspecoes", inspecaoService.total());
        model.addAttribute("ultimasInspecoes", inspecaoService.ultimas(5));
        return "index";
    }
}
