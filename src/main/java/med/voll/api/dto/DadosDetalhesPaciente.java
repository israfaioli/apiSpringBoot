package med.voll.api.dto;

import med.voll.api.jpa.Endereco;
import med.voll.api.jpa.Paciente;

public record DadosDetalhesPaciente(Long id, String nome, String email, String telefone, String cpf, boolean status,
                                    Endereco endereco) {

    public DadosDetalhesPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.isStatus(), paciente.getEndereco());
    }
}
