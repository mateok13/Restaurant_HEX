/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.common.entities;

/**
 *
 * @author edynson muñoz, jhonfer ruiz, mateo, camilo
 */
public class CarritoG{
    private int idCarrito;
    private String nombre;
    private int precio;
    private int cantidad;
    
    public CarritoG(int idCarrito, String nombre, int precio, int cantidad) {
        this.idCarrito = idCarrito;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public CarritoG(){}

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
    
    
}
