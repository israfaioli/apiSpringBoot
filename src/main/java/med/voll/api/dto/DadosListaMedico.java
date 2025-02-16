package med.voll.api.dto;

import med.voll.api.enums.Especialidade;
import med.voll.api.jpa.Medico;

public record DadosListaMedico(Long id, String nome, String email, String crm, String telefone, boolean status,
                               Especialidade especialidade) {

    public DadosListaMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.isStatus(), medico.getEspecialidade());
    }

}
