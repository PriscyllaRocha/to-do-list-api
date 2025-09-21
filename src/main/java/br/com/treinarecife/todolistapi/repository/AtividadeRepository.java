package br.com.treinarecife.todolistapi.repository;

import br.com.treinarecife.todolistapi.model.Atividade;
import br.com.treinarecife.todolistapi.model.Pessoa;
import br.com.treinarecife.todolistapi.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByResponsavel(Pessoa pessoa);

    List<Atividade> findByStatus(Status status);

    @Query("SELECT a FROM Atividade a WHERE a.responsavel.id = :pessoaId ORDER BY a.status.nome ASC")
    List<Atividade> findByResponsavelOrderByStatusName(Long pessoaId);

    @Query("SELECT a FROM Atividade a WHERE a.status.nome = :statusNome ORDER BY a.dataFimProposta DESC")
    List<Atividade> findByStatusOrderByDataFimPropostaDesc(String statusNome);
}