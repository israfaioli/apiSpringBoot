package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import java.time.DayOfWeek;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisFechamentoClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisFechamentoClinica) {
            throw new ValidationException("Consulta fora do dia/horario funcionamento da clinica");
        }
    }
}
