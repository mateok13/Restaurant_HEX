/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.common.entities;

/**
 *representa un objeto en bytes mas especificamente imagenes, que se encuentran en la base de datos
 * y seran usadas en el proyecto
 * @author EdynsonMJ
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
    public Recurso() {
    }

    /**
     * constructor parametrizado
     * @param nombre nombre del recurso
     * @param recurso contenido en bytes
     */
    public Recurso(String nombre, byte[] recurso) {
        this.nombre = nombre;
        this.recurso = recurso;
    }
//SET AND GET
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
