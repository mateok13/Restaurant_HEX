package co.unicauca.microkernel.common.entities;

/**
 * Representa un pedido de racion
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class RacionPed {
    /**
     * identificador de la ración pedido
     */
    private int racpId;
    /**
     * Identificador del pedido
     */
    private int pedId;
    /**
     * identificador de la racion
     */
    private int racId;
    /**
     * cantidad
     */
    private int cantidad;
    
    /**
    * constructor por defecto
    */
    public RacionPed() {}
    /**
     * constructor por parametrizado
     * @param racpId identificador 
     * @param pedId identificador
     * @param racId identificador de la racion
     * @param cantidad cantidad del pedido
     */
    public RacionPed(int racpId, int pedId, int racId, int cantidad) {
        this.racpId = racpId;
        this.pedId = pedId;
        this.racId = racId;
        this.cantidad = cantidad;
    }
    /**
     * constructor parametrizado
     * @param pedId  identificador del pedido
     * @param racId identificador de la racion
     * @param cantidad  cantidad del pedido
     */
    public RacionPed(int pedId, int racId, int cantidad) {
        this.pedId = pedId;
        this.racId = racId;
        this.cantidad = cantidad;
    }

    /**
     * Getter y setter
     * @return 
     */
    public int getRacpId() {
        return racpId;
    }

    public void setRacpId(int racpId) {
        this.racpId = racpId;
    }

    public int getPedId() {
        return pedId;
    }

    public void setPedId(int pedId) {
        this.pedId = pedId;
    }

    public int getRacId() {
        return racId;
    }

    public void setRacId(int racId) {
        this.racId = racId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }   
}
