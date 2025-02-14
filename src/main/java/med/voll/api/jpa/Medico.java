package med.voll.api.jpa;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosPutMedico;
import med.voll.api.enums.Especialidade;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter //gera os metodos getters de todos os atributos
@NoArgsConstructor //gera o construtor default sem argumentos, pois JPA exige em todas as entity
@AllArgsConstructor //ter um construtor que recebe todos os campos
@EqualsAndHashCode(of = "id") //gerar o equals e hashcode em cima de um atributo especifico
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private boolean status;
    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.dadosEndereco());
        this.status = true;
    }

    public void atualizarInformacoes(DadosPutMedico dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.dadosEndereco() != null) {
            this.endereco.atualizarInformacoes(dados.dadosEndereco());
        }
    }

    public void desativar() {
        this.status = false;
    }

    public void ativar() {
        this.status = true;
    }
}
