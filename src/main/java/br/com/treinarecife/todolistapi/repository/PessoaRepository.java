package br.com.treinarecife.todolistapi.repository;

import br.com.treinarecife.todolistapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByEmail(String email);

    boolean existsByEmail(String email);
}