package co.unicauca.microkernel.common.entities;

/**
 * representa un cliente de para la aplicacion.
 * @author edynson muñoz, jhonfer ruiz, camilo mulato, james, mateo
 */
public class Cliente {
    /**
     * identificador incremental del cliente
     */
    private int idCliente;
    /**
     * nombre del cliente
     */
    private String nombre;
    /**
     * hace referencia a la direccion
     */
    private int carrera;
    /**
     * hace referenca a la direccion
     */
    private int calle;
    /**
     * para la aplicacion puede ser un comprador o cliente comun, un administrador o dueño de restaurante
     */
    private TipoClien tipo;
    /**
     * el pasword del usuario
     */
    private String contrasenia;
    /**
     * una imagen que representa al cliente
     */
    private byte [] imagen;
    /**
     * un id de restaurante si es que esta asociado a uno.
     */
    private int idrestaurante;
/**
 * constructor por defecto
 */
    public Cliente() {};
/**
 * constructor parametrizado
 * @param idCliente identificador
 * @param nombre nombre del usuario
 * @param carrera direccion
 * @param calle direccion
 * @param tipo administrador o comprador
 * @param contrasenia una contraseña para su cuenta
 * @param imagen imagen o foto que lo representa
 */
    public Cliente(int idCliente, String nombre, int carrera, int calle, TipoClien tipo, String contrasenia, byte[] imagen) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.carrera = carrera;
        this.calle = calle;
        this.tipo = tipo;
        this.contrasenia = contrasenia;
        this.imagen = imagen;
    }
    /**
     * constructor parametrizado
     * @param nombre nombre se usuario
     * @param contrasenia contraseña
     */
    public Cliente(String nombre, String contrasenia) {
       this.nombre = nombre;
       this.contrasenia = contrasenia;
    }

    //SET AND GET  
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

    public TipoClien getTipo() {
        return tipo;
    }

    public void setTipo(TipoClien tipo) {
        this.tipo = tipo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getIdrestaurante() {
        return idrestaurante;
    }

    public void setIdrestaurante(int idrestaurante) {
        this.idrestaurante = idrestaurante;
    }
    
}
