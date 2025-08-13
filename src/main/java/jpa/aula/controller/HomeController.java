package jpa.aula.controller;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
