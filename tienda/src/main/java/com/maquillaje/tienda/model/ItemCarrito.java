package com.maquillaje.tienda.model;

public class ItemCarrito {
    private Integer idProducto;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String imagenUrl;

    public ItemCarrito() {}

    public ItemCarrito(Integer idProducto, String nombre, Double precio, Integer cantidad, String imagenUrl) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenUrl = imagenUrl;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    // Calcula el subtotal de este producto
    public Double getSubtotal() { return precio * cantidad; }
}