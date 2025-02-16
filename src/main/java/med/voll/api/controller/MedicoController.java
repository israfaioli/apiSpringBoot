package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosDetalhesMedico;
import med.voll.api.dto.DadosListaMedico;
import med.voll.api.dto.DadosPutMedico;
import med.voll.api.jpa.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    //para fazer injeção de dependencias, com isto o spring sabe que ele irá instanciar e passar atributo repository dentro da nossa classe controller
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional //para salvar, atualizar ou excluir transacoes no banco de dados
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        //save é o método que faz o insert la na tabela do banco de dados

        /*
        metodo do spring que cria a uri com base no location do projeto, concatenando com path do controler e convertando pathvariable pelo o id do medico
         */
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListaMedico>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        var page = medicoRepository.findAllByStatusTrue(paginacao).map(DadosListaMedico::new);
        return ResponseEntity.ok(page);
        /*
        exemplo quando precisamos retornar uma lista sem ordenação, paginação
         */
//        return medicoRepository.findAll(paginacao).stream().map(DadosListaMedico::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosPutMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhesMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.desativar();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity ativar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.ativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesMedico(medico));
    }
}
