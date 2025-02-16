package med.voll.api.dto;

import med.voll.api.enums.Especialidade;
import med.voll.api.jpa.Endereco;
import med.voll.api.jpa.Medico;

public record DadosDetalhesMedico(Long id, String nome, String email, String telefone, String crm,
                                  Especialidade especialidade, Endereco endereco) {

    public DadosDetalhesMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
