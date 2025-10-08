package br.com.treinarecife.todolistapi.controller;

import br.com.treinarecife.todolistapi.model.Atividade;
import br.com.treinarecife.todolistapi.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {
    private final AtividadeService service;

    public AtividadeController(AtividadeService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todas as atividades cadastradas")
    @GetMapping
    public List<Atividade> listar() {
        return service.listar();
    }

    @Operation(summary = "Busca uma atividade específica pelo ID")
    @GetMapping("/{id}")
    public Atividade buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @Operation(summary = "Cria uma nova atividade")
    @PostMapping
    public ResponseEntity<Atividade> criar(@Valid @RequestBody Atividade a) {
        Atividade criado = service.criar(a);
        return ResponseEntity.created(URI.create("/api/atividades/" + criado.getId())).body(criado);
    }

    @Operation(summary = "Atualiza uma atividade existente pelo ID")
    @PutMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @Valid @RequestBody Atividade a) {
        return service.atualizar(id, a);
    }

    @Operation(summary = "Deleta uma atividade existente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Relatórios
    @Operation(summary = "Gera um relatório de atividades agrupadas por pessoa")
    @GetMapping("/relatorios/por_pessoa")
    public Map<String, Map<String, List<Atividade>>> relatorioPorPessoa() {
        return service.relatorioPorPessoa();
    }

    @Operation(summary = "Gera um relatório de atividades agrupadas por estado")
    @GetMapping("/relatorios/por_estado")
    public Map<String, List<Atividade>> relatorioPorEstado() {
        return service.relatorioPorEstado();
    }

    // Ranking
    @Operation(summary = "Exibe um ranking de pessoas que mais encerraram atividades dentro do prazo")
    @GetMapping("/ranking/encerradas_no_prazo")
    public List<Map<String, Object>> rankingEncerradasNoPrazo() {
        return service.rankingEncerradasNoPrazo();
    }

    @Operation(summary = "Sugere atividades com base no contexto atual e nível de energia do usuário")
    @GetMapping("/sugeridas")
    public List<Atividade> sugerirAtividades(
        @RequestParam String contexto,
        @RequestParam int minhaEnergia) {
    return service.sugerirAtividades(contexto, minhaEnergia);
    }

    @Operation(summary = "Sugere atividades com base no contexto, energia e duração estimada")
    @GetMapping("/sugeridas")
    public List<Atividade> sugerir(
        @RequestParam String contexto,
        @RequestParam int minhaEnergia,
        @RequestParam(required = false, defaultValue = "5") int limite) {
    return service.sugerirAtividades(contexto, minhaEnergia, limite);
    }

}