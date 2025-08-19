package jpa.aula.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.aula.model.entity.Paciente;
import jpa.aula.model.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Paciente p) {
        em.persist(p);
    }

    public Role findByNome(String nome) {
        List<Role> roles = em.createQuery("SELECT r FROM Role r WHERE r.nome = :nome", Role.class)
                .setParameter("nome", nome)
                .getResultList();
        return roles.isEmpty() ? null : roles.get(0);
    }

    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    public List<Role> findAllById(List<Long> ids) {
        return em.createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", ids)
                .getResultList();
    }

}
