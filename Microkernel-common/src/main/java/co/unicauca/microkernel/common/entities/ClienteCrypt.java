package co.unicauca.microkernel.common.entities;

import co.unicauca.microkernel.common.infra.Utilities;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa Patrón Decorator
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class ClienteCrypt extends Cliente{
    private Cliente clientCrypt;
    /**
     * constructor por defecto
     */
    public ClienteCrypt() {};
    /**
    * constructor parametrizado
    * @param idCliente identificador
    * @param nombre nombre del usuario
    * @param carrera direccion
    * @param calle direccion
    * @param tipo administrador o comprador
    * @param contrasenia una contrase�a para su cuenta
    * @param imagen imagen o foto que lo representa
    */
    public ClienteCrypt(int idCliente, String nombre, int carrera, int calle, TipoClien tipo, String contrasenia, byte[] imagen){
        try {
            clientCrypt=new Cliente(idCliente, Utilities.encriptar(nombre), carrera, calle, tipo, Utilities.encriptar(contrasenia), imagen);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ClienteCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Constructor Parametrizado
     * @param nombre
     * @param carrera
     * @param calle
     * @param tipo
     * @param contrasenia
     * @param imagen 
     */
    public ClienteCrypt(String nombre, int carrera, int calle, TipoClien tipo, String contrasenia, byte[] imagen){
        try {
            clientCrypt=new Cliente(Utilities.encriptar(nombre), carrera, calle, tipo, Utilities.encriptar(contrasenia), imagen);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ClienteCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    /**
     * constructor parametrizado
     * @param nombre nombre se usuario
     * @param contrasenia contrase�a
     */
    public ClienteCrypt(String nombre, String contrasenia){
        try {
            clientCrypt=new Cliente(Utilities.encriptar(nombre), Utilities.encriptar(contrasenia));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ClienteCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getNombre() {
        return clientCrypt.getNombre();
    }

    @Override
    public String getContrasenia() {
        return clientCrypt.getContrasenia();
    }
    
    @Override
    public TipoClien getTipo() {
        return clientCrypt.getTipo();
    }
    
    @Override
    public int getCarrera() {
        return clientCrypt.getCarrera();
    }

    @Override
    public void setCarrera(int carrera) {
        this.clientCrypt.setCarrera(carrera);
    }

    @Override
    public int getCalle() {
        return clientCrypt.getCalle();
    }

    @Override
    public void setCalle(int calle) {
        this.clientCrypt.setCalle(calle);
    }
    
    @Override
    public byte[] getImagen() {
        return clientCrypt.getImagen();
    }
    
    /**
     *
     * @param imagen
     */
    @Override
    public void setImagen(byte[] imagen) {
        this.clientCrypt.setImagen(imagen);
    }
}
