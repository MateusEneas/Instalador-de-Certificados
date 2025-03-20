package com.secran.certificados.model;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {
    ADMIN,
    PADRAO;

    @Override
    public String getAuthority() {
        return name(); // Retorna o nome do enum (ADMIN ou PADRAO)
    }
}