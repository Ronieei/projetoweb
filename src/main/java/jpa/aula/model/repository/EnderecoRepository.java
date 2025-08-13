package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.aula.model.entity.Agenda;
import jpa.aula.model.entity.Endereco;
import org.springframework.stereotype.Repository;

@Repository
public class EnderecoRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Endereco endereco) {
        em.persist(endereco);
    }

}
