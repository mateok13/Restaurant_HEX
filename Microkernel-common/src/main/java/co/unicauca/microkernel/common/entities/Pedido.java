package co.unicauca.microkernel.common.entities;

import java.time.LocalDateTime;

/**
 * Representa el pediado realizado por un cliente
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Pedido {
    /**
     * identificador del padido
     */
    private int idPedido;
    /**
     * identificador del cliente
     */
    private int cliente;
    /**
     * identificador del restaurante
     */
    private int resId;
    /**
     * estado del pedido
     */
    private EstadoPed estado;
    /**
     * fecha de creacion del pedido
     */
    private LocalDateTime fechaCreado;
    /**
     * fecha de pado del pedido
     */
    private LocalDateTime fechaPagado;    
    /**
     * constructor por defecto
     */
    public Pedido() {}
    /**
     * constructor parametrizado pedido
     * @param idPedido identificador del pedido
     * @param cliente identificador del cliente
     * @param resId identificador del restaurante
     * @param estado estado del pedido
     * @param fechaCreado fecha de cracion
     * @param fechaPagado fecha de pago
     */
    public Pedido(int idPedido, int cliente, int resId, EstadoPed estado, LocalDateTime fechaCreado, LocalDateTime fechaPagado) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.resId = resId;
        this.estado = estado;
        this.fechaCreado = fechaCreado;
        this.fechaPagado = fechaPagado;
    }
    /**
     * constructor parametrizado para cliente y restaurante
     * @param idCliente identificador del cliente
     * @param resId identificador del restaurante
     */
    public Pedido(int idCliente, int resId) {
        this.cliente = idCliente;
        this.resId = resId;
    }
    /**
     * Constructor Parametrizado
     * @param idPedido
     * @param cliente
     * @param resId 
     */
    public Pedido(int idPedido, int cliente, int resId) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.resId = resId;
    }

    /**
     * Getter y setter
     * @return 
     */
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public EstadoPed getEstado() {
        return estado;
    }

    public void setEstado(EstadoPed estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public LocalDateTime getFechaPagado() {
        return fechaPagado;
    }

    public void setFechaPagado(LocalDateTime fechaPagado) {
        this.fechaPagado = fechaPagado;
    }  
}
