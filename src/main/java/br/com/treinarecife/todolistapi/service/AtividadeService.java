package br.com.treinarecife.todolistapi.service;

import br.com.treinarecife.todolistapi.model.Atividade;
import br.com.treinarecife.todolistapi.model.Pessoa;
import br.com.treinarecife.todolistapi.model.Status;
import br.com.treinarecife.todolistapi.repository.AtividadeRepository;
import br.com.treinarecife.todolistapi.repository.PessoaRepository;
import br.com.treinarecife.todolistapi.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AtividadeService {
    private final AtividadeRepository atividadeRepository;
    private final PessoaRepository pessoaRepository;
    private final StatusRepository statusRepository;

    public AtividadeService(AtividadeRepository atividadeRepository,
            PessoaRepository pessoaRepository, StatusRepository statusRepository) {
        this.atividadeRepository = atividadeRepository;
        this.pessoaRepository = pessoaRepository;
        this.statusRepository = statusRepository;
    }

    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    public Atividade buscar(Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));
    }

    @Transactional
    public Atividade criar(Atividade a) {
        Pessoa p = pessoaRepository.findById(a.getResponsavel().getId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Status s = statusRepository.findById(a.getStatus().getId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        a.setResponsavel(p);
        a.setStatus(s);
        return atividadeRepository.save(a);
    }

    @Transactional
    public Atividade atualizar(Long id, Atividade novo) {
        Atividade existente = buscar(id);
        boolean mudouParaConcluido = !Objects.equals(existente.getStatus().getNome(), "concluído")
                && Objects.equals(novo.getStatus().getNome(), "concluído");

        // atualizar campos
        existente.setTitulo(novo.getTitulo());
        existente.setDataInicio(novo.getDataInicio());
        existente.setDataFimProposta(novo.getDataFimProposta());

        // atualizar status e quando virar concluído setar dataFimEncerramento
        Status s = statusRepository.findById(novo.getStatus().getId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        existente.setStatus(s);
        if (mudouParaConcluido) {
            existente.setDataFimEncerramento(OffsetDateTime.now());
        } else if (!Objects.equals(novo.getStatus().getNome(), "concluído")) {
            // se status diferente de concluído, limpar dataFimEncerramento
            existente.setDataFimEncerramento(null);
        }

        // atualizar responsável se informado
        if (novo.getResponsavel() != null) {
            Pessoa p = pessoaRepository.findById(novo.getResponsavel().getId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            existente.setResponsavel(p);
        }

        return atividadeRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        atividadeRepository.deleteById(id);
    }

    // Relatório por pessoa (mapa pessoa -> mapa status -> lista de atividades).
    // Ordena estados em ordem executando, programado, concluído
    public Map<String, Map<String, List<Atividade>>> relatorioPorPessoa() {
        List<Status> statusOrder = new ArrayList<>();
        statusOrder.add(statusRepository.findByNome("executando").orElseThrow());
        statusOrder.add(statusRepository.findByNome("programado").orElseThrow());
        statusOrder.add(statusRepository.findByNome("concluído").orElseThrow());

        List<Pessoa> pessoas = pessoaRepository.findAll().stream()
                .sorted(Comparator.comparing(Pessoa::getNome)).collect(Collectors.toList());

        Map<String, Map<String, List<Atividade>>> resultado = new LinkedHashMap<>();
        for (Pessoa p : pessoas) {
            List<Atividade> atvs = atividadeRepository.findByResponsavel(p);
            Map<String, List<Atividade>> grouped = new LinkedHashMap<>();
            for (Status s : statusOrder) {
                List<Atividade> lista = atvs.stream()
                        .filter(a -> a.getStatus().getNome().equalsIgnoreCase(s.getNome()))
                        .collect(Collectors.toList());
                grouped.put(s.getNome(), lista);
            }
            resultado.put(p.getNome(), grouped);
        }
        return resultado;
    }

    // Relatório por estado: mapa estado -> lista atividades ordenadas por dataFimProposta (mais
    // recente -> mais futura)
    public Map<String, List<Atividade>> relatorioPorEstado() {
        List<Status> todos = statusRepository.findAll();
        Map<String, List<Atividade>> resultado = new LinkedHashMap<>();
        for (Status s : todos) {
            List<Atividade> lista =
                    atividadeRepository.findByStatusOrderByDataFimPropostaDesc(s.getNome());
            resultado.put(s.getNome(), lista);
        }
        return resultado;
    }

    // Ranking por pessoa: número de atividades encerradas no prazo (dataFimEncerramento <=
    // dataFimProposta)
    public List<Map<String, Object>> rankingEncerradasNoPrazo() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<Map<String, Object>> ranking = new ArrayList<>();
        for (Pessoa p : pessoas) {
            long count = atividadeRepository.findByResponsavel(p).stream()
                    .filter(a -> a.getDataFimEncerramento() != null
                            && a.getDataFimProposta() != null
                            && !a.getDataFimEncerramento().isAfter(a.getDataFimProposta()))
                    .count();
            Map<String, Object> entry = new HashMap<>();
            entry.put("pessoa", p.getNome());
            entry.put("email", p.getEmail());
            entry.put("encerradasNoPrazo", count);
            ranking.add(entry);
        }
        // ordenar decrescente pelo count
        ranking.sort((a, b) -> Long.compare((Long) b.get("encerradasNoPrazo"),
                (Long) a.get("encerradasNoPrazo")));
        return ranking;
    }
}