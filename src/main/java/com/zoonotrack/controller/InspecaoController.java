package com.zoonotrack.controller;

import com.zoonotrack.model.Animal;
import com.zoonotrack.model.Inspecao;
import com.zoonotrack.model.InspecaoItem;
import com.zoonotrack.model.StatusInspecao;
import com.zoonotrack.model.TipoFoco;
import com.zoonotrack.service.AgenteSaudeService;
import com.zoonotrack.service.AnimalService;
import com.zoonotrack.service.InspecaoService;
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

import java.util.Collections;

@Controller
@RequestMapping("/inspecoes")
public class InspecaoController {

    @Autowired
    private InspecaoService inspecaoService;

    @Autowired
    private MoradorService moradorService;

    @Autowired
    private AgenteSaudeService agenteSaudeService;

    @Autowired
    private AnimalService animalService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", inspecaoService.listarTodas());
        return "inspecoes/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        prepararFormulario(model, inspecaoService.prepararNova(), new InspecaoItem());
        return "inspecoes/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        prepararFormulario(model, inspecaoService.buscarPorId(id), new InspecaoItem());
        return "inspecoes/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Inspecao inspecao, RedirectAttributes redirectAttributes) {
        Inspecao salvo = inspecaoService.salvar(inspecao);
        redirectAttributes.addFlashAttribute("mensagem", "Inspeção salva!");
        return "redirect:/inspecoes/editar/" + salvo.getId();
    }

    @PostMapping("/{id}/itens")
    public String adicionarItem(@PathVariable Long id,
                                @ModelAttribute("novoItem") InspecaoItem novoItem,
                                RedirectAttributes redirectAttributes) {
        inspecaoService.adicionarItem(id, novoItem);
        redirectAttributes.addFlashAttribute("mensagem", "Item incluído.");
        return "redirect:/inspecoes/editar/" + id;
    }

    @GetMapping("/{inspecaoId}/itens/{itemId}/excluir")
    public String removerItem(@PathVariable Long inspecaoId,
                              @PathVariable Long itemId,
                              RedirectAttributes redirectAttributes) {
        inspecaoService.removerItem(itemId);
        redirectAttributes.addFlashAttribute("mensagem", "Item removido.");
        return "redirect:/inspecoes/editar/" + inspecaoId;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        inspecaoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Inspeção excluída.");
        return "redirect:/inspecoes";
    }

    private void prepararFormulario(Model model, Inspecao inspecao, InspecaoItem novoItem) {
        novoItem.setNivelRisco(1);
        if (novoItem.getAnimal() == null) {
            novoItem.setAnimal(new Animal());
        }
        model.addAttribute("inspecao", inspecao);
        model.addAttribute("novoItem", novoItem);
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("agentes", agenteSaudeService.listarTodos());
        model.addAttribute("status", StatusInspecao.values());
        model.addAttribute("tiposFoco", TipoFoco.values());
        model.addAttribute("animaisMorador", inspecao.getMorador() != null
                ? animalService.listarPorMorador(inspecao.getMorador())
                : Collections.emptyList());
    }
}
