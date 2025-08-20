package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jpa.aula.model.entity.Consulta;
import jpa.aula.model.entity.Medico;
import jpa.aula.model.entity.Paciente;
import jpa.aula.model.entity.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Medico m) {
        // Salva um novo médico no banco de dados.
        em.persist(m);
    }

    public Medico medico(Long id) {
        // Busca um médico pelo ID.
        return em.find(Medico.class, id);
    }

    public List<Medico> medicoList() {
        // Retorna a lista de todos os médicos cadastrados.
        Query query = em.createQuery("from Medico");
        return query.getResultList();
    }

    public void remove(Long id) {
        // Remove um médico do banco de dados com base no ID.
        Medico m = em.find(Medico.class, id);
        em.remove(m);
    }

    public void update(Medico m) {
        // Atualiza os dados de um médico existente.
        em.merge(m);
    }

    public List<Consulta> consultaListDoMedico(Long id) {
        // Retorna a lista de consultas associadas a um médico específico pelo ID.
        Query query = em.createQuery("SELECT c FROM Consulta c WHERE c.medico.id = :id", Consulta.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Medico> buscarMedicosPeloNome(String nome) {
        // Busca médicos cujo nome contenha o valor informado (filtro com LIKE).
        Query query = em.createQuery("from Medico where nome like :nome", Medico.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    public Optional<Medico> buscandoMedicoPeloNomeDeUsuario(String username) {
        TypedQuery<Medico> query = em.createQuery(
                "SELECT m FROM Medico m WHERE m.usuario.login = :username",
                Medico.class
        );
        query.setParameter("username", username);

        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}