package co.unicauca.microkernel.common.entities;

/**
 * representa un pedido especial
 *
 * @author edynson, camilo, jhonfer, james, mateo
 */
public class PlatoEspecialPed {

    /**
     * identificador del palto especial
     */
    private int plaepId;
    /**
     * identificador del pedido
     */
    private int pedId;
    private int plaeId;
    /**
     * cantidad
     */
    private int cantidad;

    /**
     *
     * @param plaepId
     * @param pedId
     * @param plaeId
     * @param cantidad
     */
    public PlatoEspecialPed(int plaepId, int pedId, int plaeId, int cantidad) {
        this.plaepId = plaepId;
        this.pedId = pedId;
        this.plaeId = plaeId;
        this.cantidad = cantidad;
    }

    public PlatoEspecialPed(int pedId, int plaeId, int cantidad) {
        this.pedId = pedId;
        this.plaeId = plaeId;
        this.cantidad = cantidad;
    }
 //SET AND GET
    public PlatoEspecialPed() {
    }

    public int getPlaepId() {
        return plaepId;
    }

    public void setPlaepId(int plaepId) {
        this.plaepId = plaepId;
    }

    public int getPedId() {
        return pedId;
    }

    public void setPedId(int pedId) {
        this.pedId = pedId;
    }

    public int getPlaeId() {
        return plaeId;
    }

    public void setPlaeId(int plaeId) {
        this.plaeId = plaeId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
