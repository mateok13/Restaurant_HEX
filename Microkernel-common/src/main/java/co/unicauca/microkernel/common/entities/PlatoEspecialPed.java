package co.unicauca.microkernel.common.entities;

/**
 * Representa un pedido especial
 * @author Edynson, Jhonfer, Mateo, Camilo, James
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
    /**
     * identificador del plato pedido
     */
    private int plaeId;
    /**
     * cantidad
     */
    private int cantidad;
    
    /**
     * Constructor por Defecto
     */
    public PlatoEspecialPed() {}
    /**
     * Constructor Parametrizado
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
    /**
     * COnstructor Parametrizado
     * @param pedId
     * @param plaeId
     * @param cantidad 
     */
    public PlatoEspecialPed(int pedId, int plaeId, int cantidad) {
        this.pedId = pedId;
        this.plaeId = plaeId;
        this.cantidad = cantidad;
    }
    
    /**
     * Getter y setter
     * @return 
     */    
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
