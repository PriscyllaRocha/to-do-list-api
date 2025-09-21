package br.com.treinarecife.todolistapi.repository;

import br.com.treinarecife.todolistapi.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByNome(String nome);
}