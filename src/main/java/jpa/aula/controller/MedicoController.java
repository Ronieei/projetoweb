package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.Endereco;
import jpa.aula.model.entity.Medico;
import jpa.aula.model.entity.Paciente;
import jpa.aula.model.entity.Status;
import jpa.aula.model.repository.MedicoRepository;
import jpa.aula.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("medico")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/form")
    public ModelAndView form(ModelMap model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("medico",  new Medico());

        return new ModelAndView("/medico/form", model);
    }


    // VISUALIZAR LISTA DE MEDICO
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("medicos", repository.medicoList());
        model.addAttribute("status", Status.AGENDADA);
        return new ModelAndView("/medico/list", model);
    }
    // OPERRAÇÕES CRUD
    @PostMapping("/save")
    public ModelAndView save(@Valid Medico medico, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("/medico/form", "medico", medico);
        }
        repository.save(medico);
        return new ModelAndView("redirect:/medico/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/medico/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("medico", repository.medico(id));
        return new ModelAndView("/medico/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Medico medico, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/medico/form", "medico", medico);
        }
        repository.update(medico);
        return new ModelAndView("redirect:/medico/list");
    }

    // FILTRO DE PESQUISA - PELO NOME DO MEDICO
    @GetMapping("/pesquisar")
    public ModelAndView consultaDeMedicosPorNome(@RequestParam("nome") String nome, ModelMap model) {
        if (nome == null || nome.isBlank() || nome.isEmpty()){
            return new ModelAndView("redirect:/medico/list");
        }
        model.addAttribute("medicos", repository.buscarMedicosPeloNome(nome));
        return new ModelAndView("/medico/list", model);
    }
}
