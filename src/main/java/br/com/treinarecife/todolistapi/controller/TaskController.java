package br.com.treinarecife.todolistapi.controller;

import br.com.treinarecife.todolistapi.dto.SugestaoResponse;
import br.com.treinarecife.todolistapi.model.Task;
import br.com.treinarecife.todolistapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Cria uma nova tarefa")
    @PostMapping
    public ResponseEntity<Task> criar(@RequestBody Task task) {
        return ResponseEntity.ok(service.criar(task));
    }

    @Operation(summary = "Lista todas as tarefas")
    @GetMapping
    public ResponseEntity<List<Task>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Lista tarefas por contexto (Ex: 'Em casa')")
    @GetMapping("/contexto/{contexto}")
    public ResponseEntity<List<Task>> listarPorContexto(@PathVariable String contexto) {
        return ResponseEntity.ok(service.listarPorContexto(contexto));
    }

    @Operation(summary = "Sugere a melhor tarefa com base no contexto e energia do usuário")
    @GetMapping("/sugerir")
    public ResponseEntity<SugestaoResponse> sugerirMelhorTarefa(
            @RequestParam String contexto,
            @RequestParam Integer energia) {

        return service.sugerirMelhorTarefa(contexto, energia)
                .map(task -> ResponseEntity.ok(new SugestaoResponse(null, task)))
                .orElseGet(() -> ResponseEntity.ok(
                        new SugestaoResponse("Nenhuma tarefa compatível com o seu nível de energia.", null)
                ));
    }

    @Operation(summary = "Deleta uma tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
