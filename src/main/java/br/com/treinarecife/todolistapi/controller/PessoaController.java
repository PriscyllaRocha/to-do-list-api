package br.com.treinarecife.todolistapi.controller;

import br.com.treinarecife.todolistapi.model.Pessoa;
import br.com.treinarecife.todolistapi.service.PessoaService;
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

    @GetMapping
    public List<Pessoa> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Pessoa buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa p) {
        Pessoa criado = service.criar(p);
        return ResponseEntity.created(URI.create("/api/pessoas/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa p) {
        return service.atualizar(id, p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Avatar endpoints
    @GetMapping("/{id}/avatar")
    public String getAvatar(@PathVariable Long id) {
        return service.buscar(id).getAvatar();
    }

    @PutMapping("/{id}/avatar/atualizar")
    public Pessoa atualizarAvatar(@PathVariable Long id) {
        return service.atualizarAvatar(id);
    }
}