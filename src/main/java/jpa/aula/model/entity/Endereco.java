package jpa.aula.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * é um termo que designa um terreno ou um espaço anexo a uma habitação, usado para serventia da casa,
    * ou ainda qualquer espaço público comum que pode ser usufruído por toda a população e reconhecido pela administração de um município,
    * como largos, praças, ruas, jardins, parques, entre outros.
    */
    @NotBlank(message = "Logradouro precisa esta preenchido")
    String logradouro;
    /*
    * Bairro é uma comunidade ou região dentro de uma localidade, sendo a unidade mínima de urbanização existente na maioria das cidades do mundo.
    */
    @NotBlank(message = "Bairro precisa esta preenchido")
    String bairro;

    @NotNull(message = "Selecione uma cidade")
    @ManyToOne
    Cidade cidade;

}
