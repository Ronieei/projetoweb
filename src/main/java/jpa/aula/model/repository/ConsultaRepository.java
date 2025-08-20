package jpa.aula.model.repository;

import jakarta.persistence.*;
import jpa.aula.config.ConfiguracaoSpringMVC;
import jpa.aula.model.entity.*;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ConsultaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Consulta c){
        em.persist(c);
    }

    public void remove(Long id){
        Consulta p = em.find(Consulta.class, id);
        em.remove(p);
    }

    public void update(Consulta c){
        em.merge(c);
    }

    public Consulta consulta(Long id){
        return em.find(Consulta.class, id);
    }

    public List<Consulta> consultaList(){
        Query query = em.createQuery("from Consulta ");
        return query.getResultList();
    }

    public void cancelarConsulta(Long id) {
        Consulta consulta = em.find(Consulta.class, id);
        if (consulta != null) {
            consulta.setStatus(Status.CANCELADA);
            em.merge(consulta);
        }
    }

    public void confirmarConsulta(Long id) {
        Consulta consulta = em.find(Consulta.class, id);
        if (consulta != null) {
            consulta.setStatus(Status.CONFIRMADA);
            em.merge(consulta);
        }
    }

    public void concluirConsulta(Long id) {
        Consulta consulta = em.find(Consulta.class, id);
        if (consulta != null && consulta.getStatus() == Status.CONFIRMADA) {
            consulta.setStatus(Status.CONCLUIDA);
            em.merge(consulta);
        }
    }

    public List<Consulta> listarConsultasCanceladasPorId(Long id) {
        TypedQuery<Consulta> query = em.createQuery(
                "from Consulta c where c.id = :id and c.status = :status", Consulta.class
        );
        query.setParameter("id", id);
        query.setParameter("status", Status.CANCELADA);
        return query.getResultList();
    }

    public List<Consulta> buscarConsultasPorPaciente(String nome){
        Query query = em.createQuery("from Consulta where paciente.nome like :nome");
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    public List<Consulta> pesquisarPorStatus(Status status) {
        Query query = em.createQuery("SELECT c FROM Consulta c WHERE c.status = :status", Consulta.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public List<Consulta> buscarTodasConsultasDeTodosMedicos(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Status status) {

        String jpql = """
        SELECT c FROM Consulta c
        WHERE c.data BETWEEN :dataInicial AND :dataFinal
          AND c.horarioInicio >= :horarioInicio
          AND c.horarioFim <= :horarioFim
          AND c.status = :status
    """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("status", status);

        return query.getResultList();
    }

    public Consulta criarConsultaAPartirDeAgenda(Agenda agenda) {
        Consulta consulta = new Consulta();
        consulta.setData(agenda.getData());
        consulta.setHorarioInicio(agenda.getHorarioInicio());
        consulta.setHorarioFim(agenda.getHorarioFim());
        consulta.setMedico(agenda.getMedico());
        consulta.setPaciente(agenda.getPaciente());
        consulta.setStatus(Status.CONFIRMADA);
        em.persist(consulta);
        return consulta;
    }

    public List<Consulta> buscarTodasConsultasDeTodosMedicoseTodosStatus(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim) {

        String jpql = """
        SELECT c FROM Consulta c
        WHERE c.data BETWEEN :dataInicial AND :dataFinal
        AND c.horarioInicio >= :horarioInicio
        AND c.horarioFim <= :horarioFim
    """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);

        return query.getResultList();
    }

    public List<Consulta> buscarTodasConsultasDeTodosMedicoseDoStatusSelecionado(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Status statusSelecionado) {

        String jpql = """
        SELECT c FROM Consulta c
        WHERE c.data BETWEEN :dataInicial AND :dataFinal
        AND c.horarioInicio >= :horarioInicio
        AND c.horarioFim <= :horarioFim AND  c.status = :statusSelecionado
    """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("statusSelecionado", statusSelecionado);

        return query.getResultList();
    }

    public List<Consulta> buscaTodasConsultasDeUmMedicoeTodosStatus(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Long medicoId) {

        String jpql = """
        SELECT c FROM Consulta c
        WHERE c.data BETWEEN :dataInicial AND :dataFinal
        AND c.horarioInicio >= :horarioInicio
        AND c.horarioFim <= :horarioFim 
        AND  c.medico.id = :medicoId 
    """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("medicoId", medicoId);

        return query.getResultList();
    }

    public List<Consulta> buscaTodasConsultasDeUmMedicoeUmStatus(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Status statusSelecionado,
            Long medicoId) {

        String jpql = """
        SELECT c FROM Consulta c
        WHERE c.data BETWEEN :dataInicial AND :dataFinal
        AND c.horarioInicio >= :horarioInicio
        AND c.horarioFim <= :horarioFim 
        AND  c.medico.id = :medicoId
        AND c.status = :statusSelecionado
    """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("medicoId", medicoId);
        query.setParameter("statusSelecionado", statusSelecionado);

        return query.getResultList();
    }

    public List<Consulta> buscarConsultasDoMedico(
            Long medicoId,
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Status status) {

        String jpql = "SELECT c FROM Consulta c " +
                "WHERE c.medico.id = :medicoId " +
                "AND c.data BETWEEN :dataInicial AND :dataFinal " +
                "AND ((c.horarioInicio BETWEEN :horarioInicio AND :horarioFim) " +
                "     OR (c.horarioFim BETWEEN :horarioInicio AND :horarioFim)) " +
                "AND c.status = :status";

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("medicoId", medicoId);
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("status", status);

        return query.getResultList();
    }
}