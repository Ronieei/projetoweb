package jpa.aula;

import org.junit.jupiter.api.Test;


public class DisponibilidadeRepositoryTest {

    // teste para dados ja existente no banco nao deve criar a disponibilidade
    @Test
    public void naoDeveCriarUmaDisponibilidadeComDataInicioFim_Igual_E_QueHorarioInicoFim_Igual_Tambem() {
        /* Teste uma data que tem no import sql a data e horario e intervalo de trabalho usada no teste deve ser extamente igual ao que tem no import nao deve permiti
        * criar disponibilidade ja que data e horario sao iguais e mesmo que o intervalo seja zero ou o intervalo nao seja zero */
    }



    @Test
    public void naoDeveCriarUmaDisponibilidadeComDataFim_QueEsteja_entreDentro_EssaDataInicioFimEHorario_de_IntervaloInicioFim_EHorarioINicioEFim() {
        /*
        Nao deve perrmite criar uma disponibilidad onde a data esta dentro (entre) uma data que ja tenha em disponibilidade logo uma data ja existente em disponibhlida
        o mesmo vale para horario ou intervalo onde esteja dentro (entre) um horario ja existene dessa data ou intevalo
        */
    }
}
