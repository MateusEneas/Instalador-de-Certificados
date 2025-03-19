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

    private String nomeEmpresa;
    private String caminhoArquivo;
    private String senhaCriptografada;
    private LocalDate dataExpiracao;
    private boolean disponivel;

    public Certificado() {
    }

    public Certificado(UUID id, String nomeEmpresa, String caminhoArquivo, String senhaCriptografada, LocalDate dataExpiracao, boolean disponivel) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.caminhoArquivo = caminhoArquivo;
        this.senhaCriptografada = senhaCriptografada;
        this.dataExpiracao = dataExpiracao;
        this.disponivel = disponivel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}