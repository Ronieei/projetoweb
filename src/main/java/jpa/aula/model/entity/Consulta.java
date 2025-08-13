package jpa.aula.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horarioInicio;

    @Column(nullable = false)
    private LocalTime horarioFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "medico_id_pessoa", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id_pessoa")
    private Paciente paciente;
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exame> exames = new ArrayList<>();

}