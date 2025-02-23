package med.voll.api.repository;

import java.time.LocalDateTime;
import med.voll.api.jpa.AgendamentoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoConsultaRepository extends JpaRepository<AgendamentoConsulta, Long> {
    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
