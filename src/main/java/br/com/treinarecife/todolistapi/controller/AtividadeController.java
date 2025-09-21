package br.com.treinarecife.todolistapi.controller;

import br.com.treinarecife.todolistapi.model.Atividade;
import br.com.treinarecife.todolistapi.service.AtividadeService;
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

    @GetMapping
    public List<Atividade> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Atividade buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Atividade> criar(@Valid @RequestBody Atividade a) {
        Atividade criado = service.criar(a);
        return ResponseEntity.created(URI.create("/api/atividades/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @Valid @RequestBody Atividade a) {
        return service.atualizar(id, a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Relatórios
    @GetMapping("/relatorios/por_pessoa")
    public Map<String, Map<String, List<Atividade>>> relatorioPorPessoa() {
        return service.relatorioPorPessoa();
    }

    @GetMapping("/relatorios/por_estado")
    public Map<String, List<Atividade>> relatorioPorEstado() {
        return service.relatorioPorEstado();
    }

    // Ranking
    @GetMapping("/ranking/encerradas_no_prazo")
    public List<Map<String, Object>> rankingEncerradasNoPrazo() {
        return service.rankingEncerradasNoPrazo();
    }
}