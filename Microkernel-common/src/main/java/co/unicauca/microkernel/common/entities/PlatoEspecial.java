package co.unicauca.microkernel.common.entities;

/**
 * Representa un plato especial
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class PlatoEspecial{
    /**
     * identificador del menu al que esta asociado
     */
    private int menuEsp;
    /**
     * identificador del plato especial
     */
    private int id_pe;
    /**
     * nombre del plato
     */
    private String nombre;
    /**
     * breve descripcion del plato
     */
    private String descripcion;
    /**
     * costo del plato
     */
    private int precio;
    /**
     * array de bytes que representan la imagen de plato
     */
    private byte [] imagen;
    /**
     * constructor por defecto
     */
    public PlatoEspecial(){};
    /**
     * contructor parametrizado para la creacion de un plato especial
     * @param descripcion descripcion que tendrta el plato
     * @param id_pe
     * @param nombre nombre del plato
     * @param precio precio para la venta
     * @param menuEsp
     * @param imagen
     */
    public PlatoEspecial(int id_pe, int menuEsp, String nombre, String descripcion, int precio,byte [] imagen) {
        this.menuEsp = menuEsp;
        this.id_pe = id_pe;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen=imagen;
    }

    /**
     * Getter y setter
     * @return 
     */
    public int getMenuEsp() {
        return menuEsp;
    }

    public void setMenuEsp(int menuEsp) {
        this.menuEsp = menuEsp;
    }

    public int getId_pe() {
        return id_pe;
    }

    public void setId_pe(int id_pe) {
        this.id_pe = id_pe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
