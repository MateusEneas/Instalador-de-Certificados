package com.secran.certificados.service;

import com.secran.certificados.model.Certificado;
import com.secran.certificados.repository.CertificadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificadoService {
    private final CertificadoRepository certificadoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    public List<Certificado> listarCertificados() {
        return certificadoRepository.findAll();
    }

    public Optional<Certificado> buscarPorId(UUID id) {
        return certificadoRepository.findById(id);
    }

    public Certificado salvar(Certificado certificado) {
        return certificadoRepository.save(certificado);
    }

    public void deletar(UUID id) {
        certificadoRepository.deleteById(id);
    }

}
