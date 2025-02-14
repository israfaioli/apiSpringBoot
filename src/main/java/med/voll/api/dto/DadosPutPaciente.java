package med.voll.api.dto;

import jakarta.validation.Valid;

public record DadosPutPaciente(
        Long id,
        String nome,
        String telefone,
        @Valid
        DadosEndereco endereco
) {
}
