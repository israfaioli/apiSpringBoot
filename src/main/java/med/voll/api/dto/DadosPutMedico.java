package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record DadosPutMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco dadosEndereco) {
}
