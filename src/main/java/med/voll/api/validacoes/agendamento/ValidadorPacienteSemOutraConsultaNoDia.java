package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.AgendamentoConsultaRepository;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsulta {
    @Autowired
    private AgendamentoConsultaRepository agendamentoConsultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = agendamentoConsultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if(pacientePossuiOutraConsultaNoDia) {
            throw new ValidationException("Paciente já possui uma consulta agendada neste dia");
        }
    }
}
