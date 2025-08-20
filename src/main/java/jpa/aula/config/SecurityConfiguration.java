package jpa.aula.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioDetailsConfig usuarioDetailsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                         //.requestMatchers("/usuario/cadastrar", "/home", "/css/**", "/js/**", "/images/**").permitAll()
                        // Role-based access
                        // requisições HTTP
                        .requestMatchers("/paciente/list").hasAnyRole("ADMIN", "SECRETARIA", "PACIENTE")
                        .requestMatchers("/paciente/form","/paciente/save").permitAll()
                        .requestMatchers("/paciente/edit/**","/paciente/update/**").hasAnyRole("PACIENTE","ADMIN")
                        .requestMatchers("/medico/**").hasAnyRole("ADMIN", "MEDICO", "SECRETARIA")
                        .requestMatchers("/consulta/**").hasAnyRole("ADMIN", "SECRETARIA","MEDICO")
                        .requestMatchers("/disponibilidade/**").hasAnyRole("ADMIN", "MEDICO","SECRETARIA")
                        .requestMatchers("/agenda/**").hasAnyRole("ADMIN", "MEDICO", "SECRETARIA","PACIENTE")
                        // All other requests require authentication
                        .requestMatchers("/h2-console/**").permitAll() // banco h2
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // desativa CSRF pro console
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // permite iframes do mesmo domínio (console usa iframe) h2
                )
                .formLogin(form -> form
                        .loginPage("/home") // Use /home como a pagina de login
                        .loginProcessingUrl("/login") //passamos como parâmetro a URL para acesso à página de login que criamos
                        .defaultSuccessUrl("/home", true) // logina bme suceddido
                        .permitAll()
                )
                // delogar
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home?logout") // Redirect para o /home depois logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );
                //.httpBasic(withDefaults())
                //.rememberMe(withDefaults()) // permite que usuario permanecam autenticados apos fechamento do navegado


        return http.build();
    }

    @Autowired
    public void configureUserDetails(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(usuarioDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o controle dessa instância como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar condificar um senha, ele já terá o que precisa configurado.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}