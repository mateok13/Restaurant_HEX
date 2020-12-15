/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.common.entities;

import java.time.LocalDateTime;

/**
 *representa el historial del pedido
 * @author james, jhonfer, camilo, mateo, edynson
 */
public class HistorialPed {
    /**
     * identificador del pedido
     */
    private int idPed;
    /**
     * nombre del pedido
     */
    private String nombre;
    /**
     * fecha en la que fue creado el pedido
     */
    private String fechaCreado;
    /**
     * fecha en la que es pagado el pedido
     */
    private String fechaPagado;

    /**
     * constructor parametrizado
     * @param idPed identificador
     * @param nombre 
     * @param fechaCreado fecha en la que se crea el pedido
     * @param fechaPagado fecha en la que se paga
     */
    public HistorialPed(int idPed, String nombre, String fechaCreado, String fechaPagado) {
        this.idPed = idPed;
        this.nombre = nombre;
        this.fechaCreado = fechaCreado;
        this.fechaPagado = fechaPagado;
    }

    /**
     * constructor por defecto
     */
    public HistorialPed() {
    }

    //SET AND GET
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getFechaPagado() {
        return fechaPagado;
    }

    public void setFechaPagado(String fechaPagado) {
        this.fechaPagado = fechaPagado;
    }

    public int getIdPed() {
        return idPed;
    }

    public void setIdPed(int idPed) {
        this.idPed = idPed;
    }
    
    
    
}
