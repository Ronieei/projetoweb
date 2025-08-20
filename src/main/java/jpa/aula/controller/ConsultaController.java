package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.*;
import jpa.aula.model.repository.AgendaRepository;
import jpa.aula.model.repository.ConsultaRepository;
import jpa.aula.model.repository.MedicoRepository;
import jpa.aula.model.repository.PacienteRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

@Scope("request")
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
    // session do professor
    @Autowired
    Consulta consulta; //na view para acessar a lista de exame, você faz um for each -> {session.consulta.exames}

    private static final Logger logger = LoggerFactory.getLogger(ConsultaController.class);

    @GetMapping("/form/{id}")
    public ModelAndView form(@PathVariable("id") Long id, Model model) {
        Agenda agenda = agendaRepository.findById(id);


        if (agenda == null || id == null) {
            return new ModelAndView("redirect:/agenda/list");
        }
        model.addAttribute("agenda", agenda);
        model.addAttribute("exame", new Exame());
        return new ModelAndView("/consulta/form");
    }

    // adição do exame
    @PostMapping("/exame/add")
    public ModelAndView addExame(@RequestParam(value = "agendaId") Long agendaId, Exame exame, Model model){
        // adiciona o exame na sessão ou bean de consulta
        consulta.getExames().add(exame);

        // adiciona a agenda no model
        Agenda agenda = agendaRepository.findById(agendaId);


        // adiciona um novo objeto exame para o form
        model.addAttribute("exame", new Exame());

        return new ModelAndView("/consulta/form", "agenda", agenda);
    }

    @GetMapping("/exame/remove/{index}")
    public ModelAndView removeExame(@RequestParam(value = "agendaId") Long agendaId,@PathVariable int index, Model model) {
        // adiciona a agenda no model
        Agenda agenda = agendaRepository.findById(agendaId);
        // remove da lista de exames na sessão
        if (index >= 0 && index < consulta.getExames().size()) {
            consulta.getExames().remove(index);
        }

        // mantém um novo objeto exame para o formulário
        model.addAttribute("exame", new Exame());

        return new ModelAndView("/consulta/form", "agenda", agenda);
    }



    @GetMapping("/list")
    public ModelAndView listar(
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicial,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
            Model model,
            Authentication authentication) {

        // Definir datas padrão se não fornecidas
        if (dataInicial == null && dataFinal == null) {
            LocalDate hoje = ConfiguracaoSpringMVC.obterDataFormatada();
            dataInicial = hoje;
            dataFinal = hoje;
        }

        // Definir horários padrão se não fornecidos
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
        model.addAttribute("status_Consulta", Status.CONFIRMADA);
        model.addAttribute("statusConsultaList", Status.values());

        // Verifica se o usuário é um médico logado
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {

            String usuarioName = authentication.getName();

            Medico medico = medicoRepository.buscandoMedicoPeloNomeDeUsuario(usuarioName)
                    .orElseThrow(() -> new UsernameNotFoundException("Médico não encontrado"));

            // Busca apenas as consultas do médico logado
            model.addAttribute("consultas",
                    consultaRepository.buscarConsultasDoMedico(
                            medico.getId(),
                            dataInicial,
                            dataFinal,
                            horarioInicio,
                            horarioFim,
                            Status.CONFIRMADA));

            // Passa apenas o médico logado
            model.addAttribute("medicos", List.of(medico));

        } else {
            // Caso não seja médico, busca todas as consultas de todos médicos
            model.addAttribute("consultas",
                    consultaRepository.buscarTodasConsultasDeTodosMedicos(
                            dataInicial,
                            dataFinal,
                            horarioInicio,
                            horarioFim,
                            Status.CONFIRMADA));

            model.addAttribute("medicos", medicoRepository.medicoList());
        }

        return new ModelAndView("/consulta/list");
    }

    @GetMapping("/pesquisar")
    public String pesquisarConsultas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime horarioFim,
            @RequestParam(required = false) Status statusSelecionado,
            @RequestParam(required = false) Long medicoId,
            Model model,
            Authentication authentication,   // <- injeta usuário logado
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

        List<Consulta> consultas = new ArrayList<>();

        // ⚡️ Verifica se usuário logado é médico
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {

            String username = authentication.getName();

            Medico medico = medicoRepository.buscandoMedicoPeloNomeDeUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Médico não encontrado"));

            medicoId = medico.getId(); // força sempre o médico logado

            if (statusSelecionado == null) {
                consultas = consultaRepository.buscaTodasConsultasDeUmMedicoeTodosStatus(
                        dataInicial, dataFinal, horarioInicio, horarioFim, medicoId);
            } else {
                consultas = consultaRepository.buscaTodasConsultasDeUmMedicoeUmStatus(
                        dataInicial, dataFinal, horarioInicio, horarioFim, statusSelecionado, medicoId);
            }

            model.addAttribute("medicoSelecionado", medicoId);

        } else {
            // ⚡️ Se não for médico, mantém a lógica original (admin, secretaria, etc.)
            boolean filtrarPorTodosMedicos = (medicoId == null);
            boolean filtrarPorTodosStatus = (statusSelecionado == null);

            if (filtrarPorTodosMedicos && filtrarPorTodosStatus) {
                consultas = consultaRepository.buscarTodasConsultasDeTodosMedicoseTodosStatus(
                        dataInicial, dataFinal, horarioInicio, horarioFim);
            } else if (filtrarPorTodosMedicos) {
                consultas = consultaRepository.buscarTodasConsultasDeTodosMedicoseDoStatusSelecionado(
                        dataInicial, dataFinal, horarioInicio, horarioFim, statusSelecionado);
            } else if (filtrarPorTodosStatus) {
                consultas = consultaRepository.buscaTodasConsultasDeUmMedicoeTodosStatus(
                        dataInicial, dataFinal, horarioInicio, horarioFim, medicoId);
            } else {
                consultas = consultaRepository.buscaTodasConsultasDeUmMedicoeUmStatus(
                        dataInicial, dataFinal, horarioInicio, horarioFim, statusSelecionado, medicoId);
            }
        }

        // atributos da view
        model.addAttribute("dataInicial", dataInicial);
        model.addAttribute("dataFinal", dataFinal);
        model.addAttribute("horarioInicio", horarioInicio);
        model.addAttribute("horarioFim", horarioFim);
        model.addAttribute("statusConsultaList", Status.values());
        model.addAttribute("status_Consulta", Status.CONFIRMADA);
        model.addAttribute("consultas", consultas);
        model.addAttribute("medicos", medicoRepository.medicoList());

        return "consulta/list";
    }


    @PostMapping("/save")
    public ModelAndView salvar(@RequestParam(value = "agendaId", required = true) Long agendaId, Model model) {
        Agenda agenda = agendaRepository.findById(agendaId);

        if (agenda == null || agenda.getPaciente() == null || agenda.getMedico() == null) {
            model.addAttribute("mensagemErro", "Slot ou paciente/medico inválido.");
            return new ModelAndView("redirect:/agenda/list");
        }

        agenda.setStatus(StatusAgenda.RESERVADO);
        agendaRepository.update(agenda);

        // Cria consulta baseada na agenda
        Consulta c = consultaRepository.criarConsultaAPartirDeAgenda(agenda);

        // Associa exames da session  a nova consulta
        for (Exame e : consulta.getExames()) {
            e.setConsulta(c);
        }
        c.getExames().addAll(consulta.getExames());

        // Salva a consulta correta
        consultaRepository.save(c);

        // Limpandos os  exames da sessão para a praxima consulta
        consulta.getExames().clear();

        model.addAttribute("mensagemSucesso", "Consulta agendada com sucesso!");
        return new ModelAndView("redirect:/consulta/list");
    }



    @PostMapping("/update")
    public ModelAndView update(@Valid Consulta consulta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pacientes", pacienteRepository.pacienteList());
            return new ModelAndView("/consulta/form", "consulta", consulta);
        }
        consulta.setStatus(Status.CONFIRMADA);
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