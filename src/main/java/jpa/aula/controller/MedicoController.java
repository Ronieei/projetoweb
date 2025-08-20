package jpa.aula.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.*;
import jpa.aula.model.repository.CidadeRepository;
import jpa.aula.model.repository.EstadoRepository;
import jpa.aula.model.repository.MedicoRepository;
import jpa.aula.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@RequestMapping("medico")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;


   //  @PreAuthorize("hasRole('ADMIN')") - restringe o metodo
   @GetMapping("/form")
   public ModelAndView form(ModelMap model, Authentication authentication) {
       model.addAttribute("medico", new Medico());
       model.addAttribute("endereco", new Endereco());
       carregarEstadosComCidades(model);

       // Se for admin, adiciona a lista de roles
       if (authentication.getAuthorities().stream()
               .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
           model.addAttribute("roles", roleRepository.findAll());
       }

       return new ModelAndView("/medico/form", model);
   }

    // VISUALIZAR LISTA DE MEDICO
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {

            String usuarioName = authentication.getName();

            Medico medico = repository.buscandoMedicoPeloNomeDeUsuario(usuarioName)
                    .orElseThrow(() -> new UsernameNotFoundException("Medico não encontrado"));

            // aqui passo como lista, para não quebrar no Thymeleaf que espera "medicos"
            model.addAttribute("medicos", List.of(medico));

        } else {
            model.addAttribute("medicos", repository.medicoList());
        }

        model.addAttribute("status", Status.CONFIRMADA);
        return new ModelAndView("/medico/list", model);
    }

    // OPERRAÇÕES CRUD
    @PostMapping("/save")
    public ModelAndView save(@Valid Medico medico,
                             BindingResult result,
                             @RequestParam(required = false) List<Long> rolesSelecionadas,
                             ModelMap model) {

        if (result.hasErrors()) {
            carregarEstadosComCidades(model);
            model.addAttribute("medico", medico);
            model.addAttribute("roles", roleRepository.findAll());
            return new ModelAndView("/medico/form");
        }

        // Criptografar senha
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(medico.getUsuario().getPassword());
        medico.getUsuario().setPassword(senhaCriptografada);

        // Associar as roles selecionadas
        if (rolesSelecionadas != null && !rolesSelecionadas.isEmpty()) {
            List<Role> roles = roleRepository.findAllById(rolesSelecionadas);
            medico.getUsuario().setRoles(roles);
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
