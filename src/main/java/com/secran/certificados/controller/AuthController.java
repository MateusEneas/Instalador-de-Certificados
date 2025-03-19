package com.secran.certificados.controller;

import com.secran.certificados.dto.LoginRequest;
import com.secran.certificados.dto.AuthResponse;
import com.secran.certificados.dto.UsuarioDTO;
import com.secran.certificados.model.Usuario;
import com.secran.certificados.security.JwtUtil;
import com.secran.certificados.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.salvar(usuarioDTO);

        // Criando um DTO para retornar
        UsuarioDTO responseDTO = new UsuarioDTO();
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setTipoUsuario(usuario.getTipoUsuario());

        return ResponseEntity.ok(responseDTO);
    }

}