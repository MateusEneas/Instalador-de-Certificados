package com.secran.certificados.controller;

import com.secran.certificados.model.Certificado;
import com.secran.certificados.service.CertificadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {
    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PADRAO')")
    public ResponseEntity<List<Certificado>> listar() {
        return ResponseEntity.ok(certificadoService.listarCertificados());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PADRAO')")
    public ResponseEntity<Certificado> buscarPorId(@PathVariable UUID id) {
        return certificadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Certificado> salvar(@RequestBody Certificado certificado) {
        return ResponseEntity.ok(certificadoService.salvar(certificado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        certificadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
