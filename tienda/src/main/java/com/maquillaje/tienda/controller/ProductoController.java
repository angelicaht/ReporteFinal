package com.maquillaje.tienda.controller;

import com.maquillaje.tienda.model.Producto;
import com.maquillaje.tienda.model.Usuario;
import com.maquillaje.tienda.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getIdRol() == null || usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        productoRepository.save(producto);
        return "redirect:/";
    }

    // Nuevo método protegido para eliminar productos por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getIdRol() == null || usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        productoRepository.deleteById(id);
        return "redirect:/";
    }
}