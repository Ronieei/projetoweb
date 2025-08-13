package jpa.aula.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne com a Consulta - muitos exames para uma consulta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @Column(nullable = false)
    private String tipo;

    @Column(length = 500)
    private String observacoes;
}
