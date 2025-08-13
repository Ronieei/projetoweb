package jpa.aula.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Configuration para indicar ao Spring que essa é uma classe de configuração.
 * Em seguida, é preciso estender a classe WebMvcConfigurerAdapter.
 * @author fagno
 *
 * {@link WebMvcConfigurer} pertence ao pacote org.springframework.web.servlet.config.annotation do Spring Framework.
 * Ela é usada para personalizar a configuração do Spring MVC em aplicações web.
 * Em vez de sobrescrever uma configuração completa, você pode implementar essa interface e
 * sobrescrever apenas os métodos que deseja personalizar, de forma modular.
 *
 * @author dev
 */
@Configuration
public class ConfiguracaoSpringMVC implements WebMvcConfigurer {
    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    /**
     * Obtém a data e hora atuais do sistema no fuso horário de São Paulo (-03:00).
     * @return LocalDateTime representando a data e hora atuais
     */
    public static LocalDateTime obterDataHoraAtual() {
        return LocalDateTime.now(FUSO_HORARIO);
    }

    public static LocalTime obterHoraAtual() {
        return LocalTime.now(FUSO_HORARIO);
    }

    /**
     * Retorna a data atual formatada como String no padrão dd/MM/yyyy.
     * @return String com a data formatada
     */
    public static LocalDate obterDataFormatada() {
        return LocalDate.now(FUSO_HORARIO);
    }

    public static LocalTime obterHoraMinima() {
        return LocalTime.MIN; // 00:00
    }

    public static LocalTime obterHoraMaxima() {
        return LocalTime.of(23, 59);
    }

    /**
     * Com a chamada a registry.addViewController(), estamos registrando um controller automático,
     * definido pelo próprio Spring, para atender a requisições direcionadas à URL / e /home. E com a chamada
     * a setViewName(), sempre que a aplicação receber uma requisição para um desses endereços, a view home,
     * criada na última aula, será exibida.
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/home"); //aponta para a página
    }


}
