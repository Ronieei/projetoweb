package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.Agenda;
import jpa.aula.model.entity.StatusAgenda;
import jpa.aula.model.repository.AgendaRepository;
import jpa.aula.model.repository.MedicoRepository;
import jpa.aula.model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@Transactional
@RequestMapping("agenda")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/form/{id}")
    public ModelAndView form(@PathVariable("id") Long id, Model model) {
        Agenda agenda = agendaRepository.findById(id);
        if (agenda == null) {
            return new ModelAndView("redirect:/consulta/list");
        }
        model.addAttribute("pacientes", pacienteRepository.pacienteList());
        model.addAttribute("agenda", agenda);
        model.addAttribute("status", StatusAgenda.DISPONIVEL);
        return new ModelAndView("/agenda/form");
    }

    @PostMapping("/update")
    public ModelAndView atualizar(@Valid Agenda agenda,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            model.addAttribute("mensagemErro", "Erro ao agendar consulta.");
            return new ModelAndView("/agenda/form");
        }

        // Verifica se o paciente foi selecionado
        if (agenda.getPaciente() == null || agenda.getPaciente().getId() == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Paciente não selecionado.");
            return new ModelAndView("redirect:/agenda/list");
        }

        // Atualiza o status da agenda
        agenda.setStatus(StatusAgenda.AGENDADO);
        agendaRepository.update(agenda);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Agendamento confirmado com sucesso!");
        return new ModelAndView("redirect:/agenda/list");
    }



    @GetMapping("/list")
    public ModelAndView listarAgendas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
            Model model) {

        data = (data != null) ? data : ConfiguracaoSpringMVC.obterDataFormatada();
        horarioInicio = (horarioInicio != null) ? horarioInicio : ConfiguracaoSpringMVC.obterHoraMinima();
        horarioFim = (horarioFim != null) ? horarioFim : ConfiguracaoSpringMVC.obterHoraMaxima();

        model.addAttribute("data", data);
        model.addAttribute("horarioInicio", horarioInicio);
        model.addAttribute("horarioFim", horarioFim);
        model.addAttribute("statusAgendaList", StatusAgenda.values());
        model.addAttribute("status_Agenda", StatusAgenda.DISPONIVEL);
        model.addAttribute("agendas", agendaRepository.buscarTodasAgendasDoDiaDisponiveis());
        model.addAttribute("medicos", medicoRepository.medicoList());

        return new ModelAndView("agenda/list");
    }

    @GetMapping("/pesquisar")
    public String pesquisarAgendas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
            @RequestParam(required = false) StatusAgenda statusSelecionado,
            @RequestParam(name = "medicoId", required = false) Long medicoId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (data == null || horarioInicio == null || horarioFim == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Preencha todos os campos obrigatórios.");
            return "redirect:/agenda/list";
        }

        List<Agenda> agendas;

        boolean filtrarPorTodosMedicos = (medicoId == null);
        boolean filtrarPorTodosStatus = (statusSelecionado == null);

        if (filtrarPorTodosMedicos && filtrarPorTodosStatus) {
            agendas = agendaRepository.buscarTodasAgendasDeTodosMedicoseTodosStatus(data, horarioInicio, horarioFim);
        } else if (filtrarPorTodosMedicos) {
            agendas = agendaRepository.buscarTodasAgendasDeTodosMedicoseDoStatusSelecionado(data, horarioInicio, horarioFim, statusSelecionado);
        } else if (filtrarPorTodosStatus) {
            agendas = agendaRepository.buscaTodasAgendasDeUmMedicoeTodosStatus(data, horarioInicio, horarioFim, medicoId);
        } else {
            agendas = agendaRepository.buscaTodasAgendasDeUmMedicoeUmStatus(data, horarioInicio, horarioFim, statusSelecionado, medicoId);
        }

        if (agendas == null || agendas.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Nenhuma agenda encontrada com os filtros informados."+ filtrarPorTodosMedicos);
            //return "redirect:/agenda/list";
        }

        model.addAttribute("data", data);
        model.addAttribute("horarioInicio", horarioInicio);
        model.addAttribute("horarioFim", horarioFim);
        model.addAttribute("statusSelecionado", statusSelecionado);
        model.addAttribute("status_Agenda", StatusAgenda.DISPONIVEL);
        model.addAttribute("statusAgendaList", StatusAgenda.values());
        model.addAttribute("medicoId", medicoId);
        model.addAttribute("medicos", medicoRepository.medicoList());
        model.addAttribute("agendas", agendas);

        return "agenda/list";
    }
}
