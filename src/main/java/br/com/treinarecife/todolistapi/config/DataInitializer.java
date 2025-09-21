package br.com.treinarecife.todolistapi.config;

import br.com.treinarecife.todolistapi.model.Status;
import br.com.treinarecife.todolistapi.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(StatusRepository statusRepository) {
        return args -> {
            if (statusRepository.findByNome("programado").isEmpty())
                statusRepository.save(new Status("programado"));
            if (statusRepository.findByNome("executando").isEmpty())
                statusRepository.save(new Status("executando"));
            if (statusRepository.findByNome("concluído").isEmpty())
                statusRepository.save(new Status("concluído"));
        };
    }
}