package co.unicauca.microkernel.common.entities;

/**
 *representa las reciones que estan asociadas a un dia de la semana
 * @author edynoson muñoz, jhonfer, mateo, james, camilo
 */
public class RacionDia {
    /**
     * identificador de la raiz
     */
    private int racId;
    /**
     * tipo de racion, entrada, principio etc
     */
    private CategoriaEnum tipo;
    /**
     * costo de la racion
     */
    private int precio;
    /**
     * nombre de la racion
     */
    private String nombre;
    /**
     * identificador del emnu al que esta asociada
     */
    private int menuId;
    /**
     * imagen de la racion en bytes
     */
    private byte [] imagen;

    /**
     * constructor parametrizado
     */
    public RacionDia(){};
    /**
     * constructor por defecto
     * @param racId identificador de la racion
     * @param tipo tipo de racion
     * @param precio costo de la porcion
     * @param nombre nombre de la racion
     * @param menuId identificador del menu al que esta asociada
     * @param imagen imagen que representa la racion
     */
    public RacionDia(int racId, CategoriaEnum tipo, int precio, String nombre, int menuId, byte [] imagen) {
        this.racId = racId;
        this.tipo = tipo;
        this.precio = precio;
        this.nombre = nombre;
        this.menuId = menuId;
        this.imagen=imagen;
    }
    //SET AND GET
    public int getRacId() {
        return racId;
    }

    public void setRacId(int racId) {
        this.racId = racId;
    }

    public CategoriaEnum getTipo() {
        return tipo;
    }

    public void setTipo(CategoriaEnum tipo) {
        this.tipo = tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
}
