package com.secran.certificados.service;

import com.secran.certificados.dto.CertificadoDTO;
import com.secran.certificados.model.Certificado;
import com.secran.certificados.repository.CertificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final CertificadoStorageService certificadoStorageService;

    @Autowired
    public CertificadoService(CertificadoRepository certificadoRepository, CertificadoStorageService certificadoStorageService) {
        this.certificadoRepository = certificadoRepository;
        this.certificadoStorageService = certificadoStorageService;
    }

    // Método para buscar um certificado por ID
    public Certificado buscarPorId(UUID id) {
        return certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
    }

    public Certificado salvar(CertificadoDTO certificadoDTO, MultipartFile arquivo) throws IOException {
        validarDataValidade(certificadoDTO.getDataValidade());

        Certificado certificado = new Certificado();
        certificado.setNome(certificadoDTO.getNome());
        certificado.setDataValidade(certificadoDTO.getDataValidade());
        certificado.setSenha(certificadoDTO.getSenha());

        // Salva o arquivo na pasta segura
        String caminhoArquivo = certificadoStorageService.salvarCertificado(arquivo, certificado.getId());
        certificado.setCaminhoArquivo(caminhoArquivo);

        return certificadoRepository.save(certificado);
    }

    public Resource carregarCertificado(UUID id) throws IOException {
        Certificado certificado = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));

        Path arquivo = Paths.get(certificado.getCaminhoArquivo());
        Resource resource = new UrlResource(arquivo.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Arquivo não encontrado ou não pode ser lido");
        }

        return resource;
    }

    public void excluirCertificado(UUID id) throws IOException {
        Certificado certificado = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));

        // Exclui o arquivo do sistema de arquivos
        Path arquivo = Paths.get(certificado.getCaminhoArquivo());
        Files.deleteIfExists(arquivo);

        // Exclui o certificado do banco de dados
        certificadoRepository.delete(certificado);
    }

    private void validarDataValidade(LocalDate dataValidade) {
        if (dataValidade.isBefore(LocalDate.now())) {
            throw new RuntimeException("Certificado expirado");
        }
    }

    public List<Certificado> listarTodos() {
        return certificadoRepository.findAll();
    }

    public Certificado atualizar(UUID id, CertificadoDTO certificadoDTO, MultipartFile arquivo) throws IOException {
        Certificado certificadoExistente = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));

        // Atualiza os campos
        certificadoExistente.setNome(certificadoDTO.getNome());
        certificadoExistente.setDataValidade(certificadoDTO.getDataValidade());
        certificadoExistente.setSenha(certificadoDTO.getSenha());

        // Se um novo arquivo for enviado, substitui o antigo
        if (arquivo != null && !arquivo.isEmpty()) {
            // Exclui o arquivo antigo
            Path arquivoAntigo = Paths.get(certificadoExistente.getCaminhoArquivo());
            Files.deleteIfExists(arquivoAntigo);

            // Salva o novo arquivo
            String caminhoArquivo = certificadoStorageService.salvarCertificado(arquivo, id);
            certificadoExistente.setCaminhoArquivo(caminhoArquivo);
        }

        return certificadoRepository.save(certificadoExistente);
    }

}