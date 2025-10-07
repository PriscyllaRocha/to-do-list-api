package br.com.treinarecife.todolistapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da tarefa é obrigatório.")
    private String titulo;

    @Positive(message = "A duração deve ser um valor positivo (em minutos).")
    private Integer duracao;

    @NotBlank(message = "O contexto é obrigatório (ex: 'Em casa', 'Online', 'Trabalho').")
    private String contexto;

    @Min(1)
    @Max(5)
    private Integer energia;

    private boolean concluida = false;
}