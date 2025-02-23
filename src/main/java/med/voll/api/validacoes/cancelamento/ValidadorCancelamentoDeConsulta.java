package med.voll.api.validacoes.cancelamento;

import med.voll.api.dto.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados);
}
