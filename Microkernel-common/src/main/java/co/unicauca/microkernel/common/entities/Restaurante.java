package co.unicauca.microkernel.common.entities;

/**
 * Representa un restaurante
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Restaurante {
    /**
     * identificador del restaurante, incremental
     */
    private int id;
    /**
     * identificador del cleinte o due�o asociado
     */
    private int idCliente;
    /**
     * codigo del restaurante, o agrupacion
     */
    private String codigo;
    /**
     * cnombre del restaurante
     */
    private String nombre;
    /**
     * imagen en un arreglo de bites que reprentan el restaurante
     */
    private byte[] imagen;
    /**
     * direccion
     */
    private int calle;
    /**
     *direccion
     */ 
    private int carrera;
    /**
     * constructor por defecto
     */
    public Restaurante() {}
    /**
    * constructor parametrizado
    * @param id identificador del rataurante
    * @param idcliente usuario asociado
    * @param codigo 
    * @param nombre
    * @param imagen imagen que representa el restaurante
    * @param carrera direccion
    * @param calle direccion
    */
    public Restaurante(int id,int idcliente, String codigo, String nombre, byte[] imagen, int carrera,int calle) {
        this.id = id;
        this.idCliente=idcliente;
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.calle = calle;
        this.carrera = carrera;
    }
    
    /**
     * Getter y setter
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public int getCarrera() {
        return carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public int getCalle() {
        return calle;
    }

    public void setCalle(int calle) {
        this.calle = calle;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }   
}
