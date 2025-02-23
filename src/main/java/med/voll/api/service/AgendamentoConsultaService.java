package med.voll.api.service;

import jakarta.validation.ValidationException;
import java.util.List;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.dto.DadosDetalhamentoConsulta;
import med.voll.api.jpa.AgendamentoConsulta;
import med.voll.api.jpa.Medico;
import med.voll.api.repository.AgendamentoConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // a classe service executa as regras de negócio e validações da aplicação
public class AgendamentoConsultaService {

    @Autowired
    private AgendamentoConsultaRepository agendamentoConsultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadorCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        /*
        codigo com as validações de integridade para agendar uma consulta
         */

        if (!pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())) {
            throw new ValidationException("Não foi encontrado paciente com o ID informado.");
        }

        if (dadosAgendamentoConsulta.idMedico() != null && !medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())) {

            throw new ValidationException("Não foi encontrado medico com o ID informado.");
        }

        validadores.forEach(v -> v.validar(dadosAgendamentoConsulta));

        var medico = escolherMedicoDisponivel(dadosAgendamentoConsulta);
        if(medico == null) {
            throw new ValidationException("Não existe médico disponivel para esta data/hora");
        }
        var paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var consulta = new AgendamentoConsulta(null, medico, paciente, dadosAgendamentoConsulta.data(), null);
        agendamentoConsultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedicoDisponivel(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idMedico() != null) {
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }

        if (dadosAgendamentoConsulta.especialidade() == null) {
            throw new ValidationException("Especialidade é obrigatória quando médico não for informado.");
        }

        return medicoRepository.escolherMedicoDisponivelNaData(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!agendamentoConsultaRepository.existsById(dados.idConsulta())) {
            throw new ValidationException("Id da consulta informado não existe!");
        }

        validadorCancelamento.forEach(validadorCancelamentoDeConsulta -> validadorCancelamentoDeConsulta.validar(dados));

        var consulta = agendamentoConsultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
