package jpa.aula.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Paciente extends Pessoa implements Serializable {

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 9, max = 13, message = "O telefone deve ser DDD 12345-1234 ")
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    List<Consulta> consultaList;

    @OneToMany(mappedBy = "paciente")
    private List<Agenda> agendaList;

}
