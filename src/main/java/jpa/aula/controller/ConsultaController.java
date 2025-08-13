package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.*;
import jpa.aula.model.repository.AgendaRepository;
import jpa.aula.model.repository.ConsultaRepository;
import jpa.aula.model.repository.MedicoRepository;
import jpa.aula.model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Transactional
@Controller
@RequestMapping("consulta")
public class ConsultaController {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping("/form/{id}")
    public ModelAndView form(@PathVariable("id") Long id, Model model) {
        Agenda agenda = agendaRepository.findById(id);
        if (agenda == null) {
            return new ModelAndView("redirect:/consulta/list");
        }
        model.addAttribute("pacientes", pacienteRepository.pacienteList());
        model.addAttribute("agenda", agenda);
        return new ModelAndView("/consulta/form");
    }

    @GetMapping("/list")
    public ModelAndView listar(@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicial,
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
                               Model model) {
        if (dataInicial == null && dataFinal == null) {
            LocalDate hoje = ConfiguracaoSpringMVC.obterDataFormatada();
            dataInicial = hoje;
            dataFinal = hoje;
        }

        if (horarioInicio == null) {
            horarioInicio = ConfiguracaoSpringMVC.obterHoraMinima();
        }
        if (horarioFim == null) {
            horarioFim = ConfiguracaoSpringMVC.obterHoraMaxima();
        }

        model.addAttribute("horarioInicio", horarioInicio);
        model.addAttribute("horarioFim", horarioFim);
        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);
        model.addAttribute("status_Agenda", StatusAgenda.DISPONIVEL);
        model.addAttribute("status_Consulta", Status.AGENDADA);
        //model.addAttribute("agendas", agendaRepository.findAll());
        model.addAttribute("consultas", consultaRepository.buscarTodasConsultasDeTodosMedicos(dataInicial, dataFinal, horarioInicio, horarioFim, Status.AGENDADA));
        model.addAttribute("medicos", medicoRepository.medicoList());
        return new ModelAndView("/consulta/list");
    }

    @GetMapping("/pesquisar")
    public String pesquisarConsultas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
            @RequestParam(required = false) Long medicoId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (dataInicial == null && dataFinal == null) {
            LocalDate hoje = ConfiguracaoSpringMVC.obterDataFormatada();
            dataInicial = hoje;
            dataFinal = hoje;
        }

        if (horarioInicio == null) {
            horarioInicio = ConfiguracaoSpringMVC.obterHoraMinima();
        }
        if (horarioFim == null) {
            horarioFim = ConfiguracaoSpringMVC.obterHoraMaxima();
        }

        if (medicoId == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Preencha todos os campos para realizar a pesquisa.");
            return "redirect:/consulta/list";
        }


        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);
        model.addAttribute("horarioInicio", horarioInicio);
        model.addAttribute("horarioFim", horarioFim);
        model.addAttribute("medicoSelecionado", medicoId);
        model.addAttribute("status_Agenda", StatusAgenda.DISPONIVEL);
        model.addAttribute("medicos", medicoRepository.medicoList());

        return "consulta/list";
    }

    @PostMapping("/save")
    public ModelAndView salvar(@RequestParam("agendaId") Long agendaId, @RequestParam("pacienteId") Long pacienteId,
                               @RequestParam(value = "observacao", required = false) String observacao, Model model) {
        Agenda agenda = agendaRepository.findById(agendaId);
        Paciente paciente = pacienteRepository.findById(pacienteId);
        if (agenda == null || paciente == null) {
            model.addAttribute("mensagemErro", "Slot ou paciente inválido.");
            return new ModelAndView("redirect:/consulta/list");
        }
        agenda.setStatus(StatusAgenda.RESERVADO);
        agendaRepository.update(agenda);
        Consulta consulta = consultaRepository.criarConsultaAPartirDeAgenda(agenda, paciente);
        if (observacao != null && !observacao.isBlank()) {
            consulta.setObservacao(observacao);
        }
        consultaRepository.save(consulta);
        model.addAttribute("mensagemSucesso", "Consulta agendada com sucesso!");
        return new ModelAndView("redirect:/consulta/list");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Consulta consulta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pacientes", pacienteRepository.pacienteList());
            return new ModelAndView("/consulta/form", "consulta", consulta);
        }
        consulta.setStatus(Status.AGENDADA);
        consultaRepository.update(consulta);
        return new ModelAndView("redirect:/consulta/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        consultaRepository.remove(id);
        return new ModelAndView("redirect:/consulta/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("consulta", consultaRepository.consulta(id));
        model.addAttribute("pacientes", pacienteRepository.pacienteList());
        model.addAttribute("medicos", medicoRepository.medicoList());
        return new ModelAndView("/consulta/form", model);
    }

    @GetMapping("/confirmar/{id}")
    public String confirmar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        consultaRepository.confirmarConsulta(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Consulta confirmada com sucesso!");
        return "redirect:/consulta/list";
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        consultaRepository.concluirConsulta(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Consulta concluída!");
        return "redirect:/medico/list";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        consultaRepository.cancelarConsulta(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Consulta cancelada com sucesso!");
        return "redirect:/consulta/list";
    }
}