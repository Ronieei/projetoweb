package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jpa.aula.model.entity.Cidade;
import jpa.aula.model.entity.Estado;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CidadeRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Cidade> ListarCidades() {
        Query query = em.createQuery(" from Cidade ");
        return query.getResultList();
    }

    public List<Cidade> ListarCidades(String nome) {
        Query query = em.createQuery(" from Cidade where nome like :nome");
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    public List<Cidade> findById(Long id) {
        Query query = em.createQuery(" from Cidade where id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Cidade> findByEstado(Estado estado) {
        Query query = em.createQuery(" from Cidade where estado = :estado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }

}
