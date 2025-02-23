package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {
    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = pacienteRepository.findStatusById(dados.idPaciente());
        if(!pacienteEstaAtivo) {
            throw new ValidationException("Consulta não pode ser agendada com paciente inativo");
        }
    }
}
