package com.maquillaje.tienda.controller;

import com.maquillaje.tienda.model.ItemCarrito;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    // 1. Ver el carrito
    @GetMapping
    public String verCarrito(Model model, HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);
        double total = carrito.stream().mapToDouble(ItemCarrito::getSubtotal).sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        return "carrito"; // Retorna la vista carrito.html
    }

    // 2. Añadir producto al carrito
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Integer idProducto,
                                   @RequestParam String nombre,
                                   @RequestParam Double precio,
                                   @RequestParam String imagen,
                                   HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        // Buscar si ya está en el carrito para sumar cantidad
        boolean existe = false;
        for (ItemCarrito item : carrito) {
            if (item.getIdProducto().equals(idProducto)) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break;
            }
        }
        if (!existe) {
            carrito.add(new ItemCarrito(idProducto, nombre, precio, 1, imagen));
        }
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    // 3. Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);
        carrito.removeIf(item -> item.getIdProducto().equals(id));
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    // 4. Procesar el pago (Checkout)
    @PostMapping("/pagar")
    public String procesarPago(@RequestParam String metodoPago, HttpSession session, Model model) {
        // Aquí iría la lógica para guardar en MySQL (Tabla Pedidos)
        // Por ahora simularemos que se guarda exitosamente y limpiamos el carrito

        session.removeAttribute("carrito"); // Vaciar carrito tras la compra
        model.addAttribute("mensaje", "¡Pedido exitoso! Pagaste con: " + metodoPago + ". El administrador ha sido notificado.");
        return "checkout_exito"; // Necesitamos crear esta pantalla de éxito
    }

    // Método auxiliar para obtener o crear la sesión del carrito
    private List<ItemCarrito> obtenerCarrito(HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }
}