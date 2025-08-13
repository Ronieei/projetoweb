package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jpa.aula.model.entity.Consulta;
import jpa.aula.model.entity.Exame;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExameRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Exame exame) {
        if (exame.getId() == null) {
            em.persist(exame);
        } else {
            em.merge(exame);
        }
    }

    public List<Exame> findAll() {
        return em.createQuery("select e from Exame e", Exame.class)
                .getResultList();
    }

    public List<Exame> findByConsulta(Consulta consulta) {
        return em.createQuery("select e from Exame e where e.consulta = :consulta", Exame.class)
                .setParameter("consulta", consulta)
                .getResultList();
    }

    public List<Exame> exameOrdenadoPorDataDaConsultaMaisAtual() {
        return em.createQuery(
                        "select e from Exame e order by e.consulta.data desc", Exame.class)
                .getResultList();
    }
}
