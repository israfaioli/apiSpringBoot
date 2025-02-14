package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Optional;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.dto.DadosListaMedico;
import med.voll.api.dto.DadosPutMedico;
import med.voll.api.jpa.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    //para fazer injeção de dependencias, com isto o spring sabe que ele irá instanciar e passar atributo repository dentro da nossa classe controller
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional //para salvar, atualizar ou excluir transacoes no banco de dados
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(dados));
        //save é o método que faz o insert la na tabela do banco de dados
    }

    @GetMapping
    public Page<DadosListaMedico> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return medicoRepository.findAllByStatusTrue(paginacao).map(DadosListaMedico::new);
        /*
        exemplo quando precisamos retornar uma lista sem ordenação, paginação
         */
//        return medicoRepository.findAll(paginacao).stream().map(DadosListaMedico::new).toList();
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosPutMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.desativar();
    }

    @PutMapping("/{id}")
    @Transactional
    public void ativar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.ativar();
    }
}
