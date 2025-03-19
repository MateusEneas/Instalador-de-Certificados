package com.secran.certificados.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "certificados")
public class Certificado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome; // Alterado de nomeEmpresa para nome
    private String caminhoArquivo;
    private String senha; // Alterado de senhaCriptografada para senha
    private LocalDate dataValidade; // Alterado de dataExpiracao para dataValidade
    private boolean disponivel;

    public Certificado() {
    }

    public Certificado(UUID id, String nome, String caminhoArquivo, String senha, LocalDate dataValidade, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.caminhoArquivo = caminhoArquivo;
        this.senha = senha;
        this.dataValidade = dataValidade;
        this.disponivel = disponivel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Certificado{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                ", senha='" + senha + '\'' +
                ", dataValidade=" + dataValidade +
                ", disponivel=" + disponivel +
                '}';
    }
}