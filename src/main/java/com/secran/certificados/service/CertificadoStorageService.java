package com.secran.certificados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class CertificadoStorageService {

    private final Path pastaSegura;

    @Autowired
    public CertificadoStorageService(Path pastaSegura) {
        this.pastaSegura = pastaSegura;
    }

    // Salva um arquivo de certificado na pasta segura
    public String salvarCertificado(MultipartFile arquivo, UUID idCertificado) throws IOException {
        String nomeArquivo = idCertificado.toString() + ".pfx"; // Nome do arquivo baseado no ID do certificado
        Path destino = pastaSegura.resolve(nomeArquivo).normalize();

        // Verifica se o caminho é seguro
        if (!destino.getParent().equals(pastaSegura.toAbsolutePath())) {
            throw new IOException("Caminho inválido para o arquivo.");
        }

        // Copia o arquivo para a pasta segura
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        return destino.toString();
    }

    // Carrega um arquivo de certificado da pasta segura
    public Resource carregarCertificado(UUID idCertificado) throws IOException {
        String nomeArquivo = idCertificado.toString() + ".pfx";
        Path arquivo = pastaSegura.resolve(nomeArquivo).normalize();

        if (!Files.exists(arquivo)) {
            throw new IOException("Arquivo não encontrado: " + nomeArquivo);
        }

        return new UrlResource(arquivo.toUri());
    }

    // Exclui um arquivo de certificado da pasta segura
    public void excluirCertificado(UUID idCertificado) throws IOException {
        String nomeArquivo = idCertificado.toString() + ".pfx";
        Path arquivo = pastaSegura.resolve(nomeArquivo).normalize();

        if (!Files.exists(arquivo)) {
            throw new IOException("Arquivo não encontrado: " + nomeArquivo);
        }

        Files.delete(arquivo);
    }
}