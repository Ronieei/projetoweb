package jpa.aula.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Medico extends Pessoa implements Serializable {

    @Column(name = "crm", nullable = false)
    @NotBlank(message = "Preenchimento obrigatorio")
    @Size(min = 8, max = 8, message = "FORMATO - CRM12345")
    private String crm;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.PERSIST)
    private List<Consulta> consultaList;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.PERSIST)
    private List<Disponibilidade> disponibilidadeLista;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.PERSIST)
    private List<Agenda> agendaList;
}