package co.unicauca.microkernel.common.entities;

/**
 * Envio
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Delivery {

    /**
     * precio de envio
     */
    private double precio;
    /**
     * distacia
     */
    private double distance;
    /**
     * codigo de agrupacion 
     */
    private String resCode;
    
    /**
     * Constructor Parametrizado
     * @param precio
     * @param distance
     * @param resCode 
     */
    public Delivery(double precio, double distance, String resCode) {
        this.precio = precio;
        this.distance = distance;
        this.resCode = resCode;
    }
    
    /**
     * Getter y setter
     * @return 
     */
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    } 
}
