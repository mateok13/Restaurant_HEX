package co.unicauca.microkernel.common.entities;

/**
 * Representa un objeto en bytes mas especificamente imagenes, que se encuentran en la base de datos
 * y serán usadas en el proyecto
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Recurso {
    /**
     * nombre del recurso o identificador
     */
    private String nombre;
    /**
     * contenido del recuro en un arreglo de bytes
     */
    private byte[] recurso;

    /**
     * constructor por defecto
     */
    public Recurso() {}
    /**
     * constructor parametrizado
     * @param nombre nombre del recurso
     * @param recurso contenido en bytes
     */
    public Recurso(String nombre, byte[] recurso) {
        this.nombre = nombre;
        this.recurso = recurso;
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

    public byte[] getRecurso() {
        return recurso;
    }

    public void setRecurso(byte[] recurso) {
        this.recurso = recurso;
    }   
}
