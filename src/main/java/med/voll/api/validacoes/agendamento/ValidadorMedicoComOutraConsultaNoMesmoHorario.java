package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.AgendamentoConsultaRepository;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private AgendamentoConsultaRepository agendamentoConsultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = agendamentoConsultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidationException("Médico já possui consulta agendada neste horário");
        }
    }
}
