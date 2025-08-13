package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jpa.aula.model.entity.Estado;
import jpa.aula.model.entity.Medico;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstadoRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Estado> ListarEstados() {
        Query query = em.createQuery(" from Estado ");
        return query.getResultList();
    }

    public Estado findById(Long id) {
        return em.find(Estado.class, id);
    }
}
