package med.voll.api.dto;

import java.time.LocalDateTime;
import med.voll.api.jpa.AgendamentoConsulta;

public record DadosDetalhamentoConsulta(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime data
) {
    public DadosDetalhamentoConsulta(AgendamentoConsulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
