package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.model.entity.*;
import jpa.aula.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/form")
    public ModelAndView form(ModelMap model) {
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("endereco", new Endereco());

        carregarEstadosComCidades(model);

        return new ModelAndView("/paciente/form", model);
    }

    @PostMapping("/save")
    public ModelAndView save( @Valid Paciente paciente, BindingResult pacienteResult, ModelMap model) {

        if (pacienteResult.hasErrors()) {
            carregarEstadosComCidades(model);
            model.addAttribute("paciente", paciente);
            return new ModelAndView("/paciente/form");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario u = paciente.getUsuario();
        String senhaCriptografada = encoder.encode(u.getPassword());
        u.setPassword(senhaCriptografada);

        // Busca a role paciente no banco e adiciona ao usuário
        Role rolePaciente = roleRepository.findByNome("ROLE_PACIENTE");
        u.getRoles().add(rolePaciente);

        paciente.setUsuario(u);

        repository.save(paciente);

        return new ModelAndView("redirect:/paciente/list");
    }


    @GetMapping("/list")
    public ModelAndView listar(ModelMap model, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {

            String usuarioName = authentication.getName();

            Paciente paciente = repository.buscandoPacientePeloNomeDeUsuario(usuarioName)
                    .orElseThrow(() -> new UsernameNotFoundException("Paciente não encontrado"));

            // aqui passo como lista, para não quebrar no Thymeleaf que espera "pacientes"
            model.addAttribute("pacientes", List.of(paciente));

        } else {
            model.addAttribute("pacientes", repository.pacienteList());
        }

        model.addAttribute("status", Status.CONFIRMADA);
        return new ModelAndView("/paciente/list", model);
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        repository.remove(id);
        return new ModelAndView("redirect:/paciente/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("paciente", repository.paciente(id));

        carregarEstadosComCidades(model);

        return new ModelAndView("/paciente/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Paciente paciente, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/paciente/form", "paciente", paciente);
        }
        repository.update(paciente);
        return new ModelAndView("redirect:/paciente/list");
    }

    @GetMapping("/pesquisar")
    public ModelAndView consultarPacientesPorNome(@RequestParam("nome") String nome, ModelMap model) {
        if (nome == null || nome.isBlank() || nome.isEmpty()) {
            model.addAttribute("status", Status.CONFIRMADA);
            return new ModelAndView("redirect:/paciente/list");
        }
        model.addAttribute("pacientes", repository.buscarPorPacientePeloNome(nome));
        model.addAttribute("status", Status.CONFIRMADA);
        return new ModelAndView("/paciente/list", model);
    }

    @GetMapping("/{id}/consultas")
    public ModelAndView listarConsultas(@PathVariable("id") Long id, ModelMap model) {
        Paciente paciente = repository.paciente(id);
        if (paciente == null) {
            return new ModelAndView("redirect:/paciente/list");
        }
        model.addAttribute("paciente", paciente);
        model.addAttribute("consultas", repository.consultaListDoPaciente(id));
        return new ModelAndView("/paciente/consultas", model);
    }

    private void carregarEstadosComCidades(ModelMap model) {
        List<Estado> estados = estadoRepository.ListarEstados();
        Map<Estado, List<Cidade>> estadosComCidades = new LinkedHashMap<>();
        for (Estado estado : estados) {
            List<Cidade> cidadesDoEstado = cidadeRepository.findByEstado(estado);
            estadosComCidades.put(estado, cidadesDoEstado);
        }
        model.addAttribute("estadosComCidades", estadosComCidades);
    }

}