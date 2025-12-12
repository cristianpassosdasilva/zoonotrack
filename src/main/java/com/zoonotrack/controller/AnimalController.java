package com.zoonotrack.controller;

import com.zoonotrack.model.Animal;
import com.zoonotrack.model.EspecieAnimal;
import com.zoonotrack.service.AnimalService;
import com.zoonotrack.service.MoradorService;
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
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private MoradorService moradorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", animalService.listarTodos());
        return "animais/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        prepararFormulario(model, new Animal());
        return "animais/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        prepararFormulario(model, animalService.buscarPorId(id));
        return "animais/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Animal animal, RedirectAttributes redirectAttributes) {
        animalService.salvar(animal);
        redirectAttributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!");
        return "redirect:/animais";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        animalService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Animal excluído.");
        return "redirect:/animais";
    }

    private void prepararFormulario(Model model, Animal animal) {
        model.addAttribute("animal", animal);
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("especies", EspecieAnimal.values());
    }
}
