package br.com.treinarecife.todolistapi.service;

import br.com.treinarecife.todolistapi.model.Task;
import br.com.treinarecife.todolistapi.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> listarTodas() {
        return repository.findAll();
    }

    public Task criar(Task task) {
        return repository.save(task);
    }

    public Optional<Task> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Task> listarPorContexto(String contexto) {
        return repository.findByContextoIgnoreCase(contexto);
    }

    // Função de sugestão inteligente
    public Optional<Task> sugerirMelhorTarefa(String contexto, Integer energiaUsuario) {
        List<Task> tarefasContexto = repository.findByContextoIgnoreCaseAndConcluidaFalse(contexto);

        return tarefasContexto.stream()
                .filter(t -> t.getEnergia() <= energiaUsuario)
                .sorted(Comparator.comparing(Task::getEnergia)
                        .thenComparing(Task::getDuracao))
                .findFirst();
    }
}
