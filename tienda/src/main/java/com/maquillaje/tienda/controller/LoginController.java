package com.maquillaje.tienda.controller;

import com.maquillaje.tienda.model.Usuario;
import com.maquillaje.tienda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndPassword(email, password);
        if (usuarioOpt.isPresent()) {
            session.setAttribute("usuarioLogueado", usuarioOpt.get());
            return "redirect:/";
        }
        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // --- NUEVO: REGISTRO DE CLIENTES CON SESIÓN ---
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute Usuario usuario,
                                   HttpSession session,
                                   Model model) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "Este correo ya está registrado. Intenta iniciar sesión.");
            return "registro";
        }

        // Rol 2 = Cliente
        usuario.setIdRol(2);

        // Guardar en MySQL y dejar iniciada su sesión
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        session.setAttribute("usuarioLogueado", usuarioGuardado);

        return "redirect:/";
    }
}