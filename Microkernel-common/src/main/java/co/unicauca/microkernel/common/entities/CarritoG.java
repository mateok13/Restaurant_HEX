package co.unicauca.microkernel.common.entities;

/**
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class CarritoG{
    private int idCarrito;
    private String nombre;
    private int precio;
    private int cantidad;
    
    public CarritoG(){}
    
    public CarritoG(int idCarrito, String nombre, int precio, int cantidad) {
        this.idCarrito = idCarrito;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
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
