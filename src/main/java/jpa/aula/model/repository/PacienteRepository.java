package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jpa.aula.model.entity.Consulta;
import jpa.aula.model.entity.Paciente;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Paciente p) {
        em.persist(p);
    }

    public Paciente paciente(Long id) {
        return em.find(Paciente.class, id);
    }

    public Paciente findById(Long id) {
        return em.find(Paciente.class, id);
    }

    public List<Paciente> pacienteList() {
        Query query = em.createQuery("from Paciente");
        return query.getResultList();
    }

    public void remove(Long id) {
        Paciente p = em.find(Paciente.class, id);
        em.remove(p);
    }

    public List<Consulta> consultaListDoPaciente(Long id) {
        Query query = em.createQuery("SELECT c FROM Consulta c WHERE c.paciente.id = :id", Consulta.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void update(Paciente p) {
        em.merge(p);
    }

    public List<Paciente> buscarPorPacientePeloNome(String nome) {
        Query query = em.createQuery("SELECT p FROM Paciente p WHERE p.nome LIKE :nome", Paciente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
}