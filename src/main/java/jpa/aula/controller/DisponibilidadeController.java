package jpa.aula.controller;

import jakarta.transaction.Transactional;

import jpa.aula.model.entity.Disponibilidade;
import jpa.aula.model.repository.ConsultaRepository;
import jpa.aula.model.repository.DisponibilidadeRepository;
import jpa.aula.model.repository.MedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Transactional
@Controller
@RequestMapping("disponibilidade")
public class DisponibilidadeController {
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping("/form")
    public ModelAndView form(Model model) {
        model.addAttribute("medicos", medicoRepository.medicoList());
        model.addAttribute("disponibilidade", new Disponibilidade());
        return new ModelAndView("disponibilidade/form");
    }

    @PostMapping("/save")
    public ModelAndView save(Disponibilidade disponibilidade) {
        disponibilidadeRepository.save(disponibilidade);
        return new ModelAndView("redirect:/agenda/list");
    }

    @PostMapping("/pesquisar-mes-ano")
    public ModelAndView pesquisarMesAno(@RequestParam("mesAno") String mesAno, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        YearMonth yearMonth = YearMonth.parse(mesAno, formatter);
        int mes = yearMonth.getMonthValue();
        int ano = yearMonth.getYear();
        model.addAttribute("disponibilidades", disponibilidadeRepository.findByMes(mes, ano));
        return new ModelAndView("disponibilidade/list");
    }
}
