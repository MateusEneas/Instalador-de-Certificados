package com.secran.certificados.repository;

import com.secran.certificados.model.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, UUID> {
}