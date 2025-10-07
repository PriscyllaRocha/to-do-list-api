package br.com.treinarecife.todolistapi.repository;

import br.com.treinarecife.todolistapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByContextoIgnoreCase(String contexto);

    List<Task> findByContextoIgnoreCaseAndConcluidaFalse(String contexto);
}
