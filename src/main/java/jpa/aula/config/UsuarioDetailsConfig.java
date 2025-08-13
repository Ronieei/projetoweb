package jpa.aula.config;

import jakarta.transaction.Transactional;
import jpa.aula.model.entity.Usuario;
import jpa.aula.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Implementação personalizada do serviço UserDetailsService do Spring Security.
 * Responsável por carregar os dados do usuário a partir do login fornecido durante a autenticação.
 */
@Transactional
@Repository
public class UsuarioDetailsConfig implements UserDetailsService {

    // Injeta automaticamente a dependência do repositório que acessa os dados do usuário
    @Autowired
    UsuarioRepository repository;

    /**
     * Método que é chamado automaticamente pelo Spring Security no momento da autenticação.
     *
     * @param login O nome de usuário (login) informado na tela de login.
     * @return Um objeto UserDetails contendo as informações de autenticação e autorização do usuário.
     * @throws UsernameNotFoundException Se nenhum usuário for encontrado com o login informado.
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados com base no login informado
        Usuario usuario = repository.usuario(login);

        // Se o usuário não for encontrado, lança exceção que será tratada pelo Spring Security
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        // Cria e retorna um objeto User do Spring Security com os dados do usuário autenticado:
        // - login
        // - senha
        // - 4 flags booleanas indicando se a conta está ativa, não expirada, não bloqueada e credenciais válidas
        // - lista de autorizações (roles/perfis) associadas ao usuário
        return new User(
                usuario.getUsername(),      // nome de login
                usuario.getPassword(),      // senha codificada (normalmente com BCrypt)
                true,                       // conta ativa (isEnabled)
                true,                       // conta não expirada
                true,                       // credenciais não expiradas
                true,                       // conta não bloqueada
                usuario.getAuthorities()    // lista de roles (implementa GrantedAuthority)
        );
    }
}
