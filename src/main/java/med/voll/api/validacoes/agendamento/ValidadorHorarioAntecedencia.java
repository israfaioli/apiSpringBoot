package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.dto.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsulta {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedencia mínima de 30 minutos");
        }
    }
}
