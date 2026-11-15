package com.maquillaje.tienda.controller;

import com.maquillaje.tienda.model.Usuario;
import com.maquillaje.tienda.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductoRepository productoRepository;

    // Ruta Principal: Carga los productos desde MySQL hacia index.html
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("productos", productoRepository.findAll());
        return "index";
    }

    // Ruta Nosotros
    @GetMapping("/nosotros")
    public String nosotros(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        return "nosotros";
    }

    // Ruta Maquillaje (Mapea a tu vista Productos.html)
    @GetMapping("/maquillaje")
    public String maquillaje(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        // Si el idRol es diferente de 1 (Administrador), lo redirige al inicio
        if (usuario.getIdRol() == null || usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        return "Productos";
    }

    // Ruta Ofertas
    @GetMapping("/ofertas")
    public String ofertas(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        return "redirect:/#catalogo";
    }
}