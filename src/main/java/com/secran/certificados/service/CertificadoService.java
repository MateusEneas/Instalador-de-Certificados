package com.secran.certificados.service;

import com.secran.certificados.dto.CertificadoDTO;
import com.secran.certificados.model.Certificado;
import com.secran.certificados.repository.CertificadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    // Listar todos os certificados
    public List<Certificado> listarTodos() {
        return certificadoRepository.findAll();
    }

    // Buscar um certificado por ID
    public Certificado buscarPorId(Long id) {
        return certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
    }

    // Salvar um novo certificado
    public Certificado salvar(CertificadoDTO certificadoDTO) {
        validarDataValidade(certificadoDTO.getDataValidade());

        Certificado certificado = new Certificado();
        certificado.setNome(certificadoDTO.getNome());
        certificado.setCaminhoArquivo(certificadoDTO.getCaminhoArquivo());
        certificado.setDataValidade(certificadoDTO.getDataValidade());
        certificado.setSenha(certificadoDTO.getSenha());

        return certificadoRepository.save(certificado);
    }

    // Atualizar um certificado existente
    public Certificado atualizar(Long id, CertificadoDTO certificadoDTO) {
        Certificado certificadoExistente = buscarPorId(id);
        validarDataValidade(certificadoDTO.getDataValidade());

        certificadoExistente.setNome(certificadoDTO.getNome());
        certificadoExistente.setCaminhoArquivo(certificadoDTO.getCaminhoArquivo());
        certificadoExistente.setDataValidade(certificadoDTO.getDataValidade());
        certificadoExistente.setSenha(certificadoDTO.getSenha());

        return certificadoRepository.save(certificadoExistente);
    }

    // Excluir um certificado
    public void excluir(Long id) {
        Certificado certificado = buscarPorId(id);
        certificadoRepository.delete(certificado);
    }

    // Validar a data de validade do certificado
    private void validarDataValidade(LocalDate dataValidade) {
        if (dataValidade.isBefore(LocalDate.now())) {
            throw new RuntimeException("Certificado expirado");
        }
    }
}