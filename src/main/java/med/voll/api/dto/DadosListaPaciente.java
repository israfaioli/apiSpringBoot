package med.voll.api.dto;

import med.voll.api.jpa.Paciente;

public record DadosListaPaciente(Long id, String nome, String email, String telefone, String cpf, boolean status) {

    public DadosListaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.isStatus());
    }
}
