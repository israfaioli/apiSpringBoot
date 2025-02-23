package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.dto.DadosDetalhamentoConsulta;
import med.voll.api.service.AgendamentoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class AgendamentoController {

    @Autowired
    private AgendamentoConsultaService agendamentoConsultaService;

    @PostMapping
    @Transactional //para salvar, atualizar ou excluir transacoes no banco de dados
    /*
    o @Valid é para realizar as validações dos atributos de cada campo no DTO, por exemplo notNUll, notBlank....
     */
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        System.out.println(dadosAgendamentoConsulta);
        var dto = agendamentoConsultaService.agendar(dadosAgendamentoConsulta);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agendamentoConsultaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
