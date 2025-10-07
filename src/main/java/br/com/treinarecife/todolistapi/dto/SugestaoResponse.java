package br.com.treinarecife.todolistapi.dto;

import br.com.treinarecife.todolistapi.model.Task;

public record SugestaoResponse(String mensagem, Task tarefa) {}