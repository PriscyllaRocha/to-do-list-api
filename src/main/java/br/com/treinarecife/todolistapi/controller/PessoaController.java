package br.com.treinarecife.todolistapi.controller;

import br.com.treinarecife.todolistapi.model.Pessoa;
import br.com.treinarecife.todolistapi.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {
    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todas as pessoas cadastradas")
    @GetMapping
    public List<Pessoa> listar() {
        return service.listar();
    }

    @Operation(summary = "Busca uma pessoa específica pelo ID")
    @GetMapping("/{id}")
    public Pessoa buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @Operation(summary = "Cria uma nova pessoa")
    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa p) {
        Pessoa criado = service.criar(p);
        return ResponseEntity.created(URI.create("/api/pessoas/" + criado.getId())).body(criado);
    }

    @Operation(summary = "Atualiza os dados de uma pessoa existente")
    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa p) {
        return service.atualizar(id, p);
    }

    @Operation(summary = "Deleta uma pessoa pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Avatar endpoints
    @Operation(summary = "Obtém o avatar de uma pessoa específica")
    @GetMapping("/{id}/avatar")
    public String getAvatar(@PathVariable Long id) {
        return service.buscar(id).getAvatar();
    }

    @Operation(summary = "Atualiza o avatar de uma pessoa específica")
    @PutMapping("/{id}/avatar/atualizar")
    public Pessoa atualizarAvatar(@PathVariable Long id) {
        return service.atualizarAvatar(id);
    }
}