package med.voll.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import med.voll.api.enums.Especialidade;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future //exigencia que seja uma data no futuro
        LocalDateTime data,

        Especialidade especialidade) {
}
