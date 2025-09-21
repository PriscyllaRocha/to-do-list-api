package br.com.treinarecife.todolistapi.service;

import br.com.treinarecife.todolistapi.model.Pessoa;
import br.com.treinarecife.todolistapi.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscar(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    @Transactional
    public Pessoa criar(Pessoa p) {
        if (pessoaRepository.existsByEmail(p.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        // se avatar não informado, cria um padrão com UUID para garantir link único
        if (p.getAvatar() == null || p.getAvatar().isBlank()) {
            // p.setAvatar("https://avatar-placeholder.iran.liara.run/" + UUID.randomUUID());
            p.setAvatar("https://avatar.iran.liara.run/public");
        }
        return pessoaRepository.save(p);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa novo) {
        Pessoa existente = buscar(id);
        if (!existente.getEmail().equals(novo.getEmail())
                && pessoaRepository.existsByEmail(novo.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        existente.setNome(novo.getNome());
        existente.setEmail(novo.getEmail());
        // não sobrescrever avatar aqui intencionalmente
        return pessoaRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    @Transactional
    public Pessoa atualizarAvatar(Long id) {
        Pessoa p = buscar(id);
        // p.setAvatar("https://avatar-placeholder.iran.liara.run/" + UUID.randomUUID());
        p.setAvatar("https://avatar.iran.liara.run/public");
        return pessoaRepository.save(p);
    }
}