package br.com.treinarecife.todolistapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Table(name = "atividade")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFimProposta;
    private OffsetDateTime dataFimEncerramento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pessoa_id")
    private Pessoa responsavel;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = OffsetDateTime.now();
    }

    // getters e setters (inclua todos)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public OffsetDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(OffsetDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public OffsetDateTime getDataFimProposta() {
        return dataFimProposta;
    }

    public void setDataFimProposta(OffsetDateTime dataFimProposta) {
        this.dataFimProposta = dataFimProposta;
    }

    public OffsetDateTime getDataFimEncerramento() {
        return dataFimEncerramento;
    }

    public void setDataFimEncerramento(OffsetDateTime dataFimEncerramento) {
        this.dataFimEncerramento = dataFimEncerramento;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }
}