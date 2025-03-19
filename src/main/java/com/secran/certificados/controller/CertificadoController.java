package com.secran.certificados.controller;

import com.secran.certificados.dto.CertificadoDTO;
import com.secran.certificados.model.Certificado;
import com.secran.certificados.service.CertificadoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    // Listar todos os certificados (acessível por USER e ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Certificado> listarCertificados() {
        return certificadoService.listarTodos();
    }

    // Buscar um certificado por ID (acessível por USER e ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Certificado buscarCertificadoPorId(@PathVariable Long id) {
        return certificadoService.buscarPorId(id);
    }

    // Adicionar um novo certificado (acessível apenas por ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Certificado adicionarCertificado(@RequestBody CertificadoDTO certificadoDTO) {
        return certificadoService.salvar(certificadoDTO);
    }

    // Atualizar um certificado existente (acessível apenas por ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Certificado atualizarCertificado(@PathVariable Long id, @RequestBody CertificadoDTO certificadoDTO) {
        return certificadoService.atualizar(id, certificadoDTO);
    }

    // Excluir um certificado (acessível apenas por ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void excluirCertificado(@PathVariable Long id) {
        certificadoService.excluir(id);
    }

    // Instalar um certificado (acessível por USER e ADMIN)
    @GetMapping("/instalar/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> instalarCertificado(@PathVariable Long id) {
        Certificado certificado = certificadoService.buscarPorId(id);
        Path path = Paths.get(certificado.getCaminhoArquivo());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}