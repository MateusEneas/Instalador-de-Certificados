package com.secran.certificados.controller;

import com.secran.certificados.dto.CertificadoDTO;
import com.secran.certificados.model.Certificado;
import com.secran.certificados.service.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;

    @Autowired
    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Certificado adicionarCertificado(
            @RequestPart CertificadoDTO certificadoDTO,
            @RequestPart MultipartFile arquivo) throws IOException {
        return certificadoService.salvar(certificadoDTO, arquivo);
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadCertificado(@PathVariable UUID id) throws IOException {
        Resource resource = certificadoService.carregarCertificado(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Certificado>> listarCertificados() {
        List<Certificado> certificados = certificadoService.listarTodos();
        return ResponseEntity.ok(certificados);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirCertificado(@PathVariable UUID id) throws IOException {
        certificadoService.excluirCertificado(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Certificado> atualizarCertificado(
            @PathVariable UUID id,
            @RequestPart CertificadoDTO certificadoDTO,
            @RequestPart(required = false) MultipartFile arquivo) throws IOException {
        Certificado certificado = certificadoService.atualizar(id, certificadoDTO, arquivo);
        return ResponseEntity.ok(certificado);
    }

}