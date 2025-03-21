package com.secran.certificados.controller;

import com.secran.certificados.dto.AuthResponse;
import com.secran.certificados.dto.LoginRequest;
import com.secran.certificados.dto.UsuarioDTO;
import com.secran.certificados.model.Usuario;
import com.secran.certificados.security.JwtUtil;
import com.secran.certificados.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        // Obtém o usuário autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Gera o token JWT
        String token = jwtUtil.generateToken(usuario);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.salvar(usuarioDTO);
        return ResponseEntity.ok(usuario);
    }
}