package med.voll.api.validacoes.cancelamento;

import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.repository.AgendamentoConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{
    @Autowired
    private AgendamentoConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidationException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
