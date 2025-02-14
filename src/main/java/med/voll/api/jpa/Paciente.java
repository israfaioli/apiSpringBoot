package med.voll.api.jpa;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DadosPutPaciente;
import med.voll.api.dto.DadosCadastroPaciente;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter //gera os metodos getters de todos os atributos
@NoArgsConstructor //gera o construtor default sem argumentos, pois JPA exige em todas as entity
@AllArgsConstructor //ter um construtor que recebe todos os campos
@EqualsAndHashCode(of = "id") //gerar o equals e hashcode em cima de um atributo especifico
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean status;
    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.status = true;
        this.endereco = new Endereco(dadosCadastroPaciente.dadosEndereco());
    }

    public void atualizarInformacoes(DadosPutPaciente dados) {
        if (dados.nome() != null)
            this.nome = dados.nome();

        if (dados.telefone() != null)
            this.telefone = dados.telefone();

        if (dados.endereco() != null)
            endereco.atualizarInformacoes(dados.endereco());
    }

    public void inativar() {
        this.status = false;
    }

    public void ativar() {
        this.status = true;
    }
}
