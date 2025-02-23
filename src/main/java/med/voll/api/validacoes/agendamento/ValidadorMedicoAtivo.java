package med.voll.api.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do medico opcional
        if(dados.idMedico() == null) {
            return;
        }
        var medicoEstaAtivo = medicoRepository.findStatusById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidationException("Consulta não pode ser agendada com médico inativo.");
        }
    }
}
