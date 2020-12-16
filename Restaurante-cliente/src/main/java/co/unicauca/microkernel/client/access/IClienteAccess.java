package co.unicauca.microkernel.client.access;

import java.util.List;
import co.unicauca.microkernel.common.entities.*;
import co.unicauca.microkernel.common.infra.Protocol;

/**
 * entidad abstracta del los servicios que el cliente puede solicitar al servidor
 * @author EdynsonMJ
 * @author Jhonny Rosero
 */
public interface IClienteAccess {
    
    public String saveRestaurant(Restaurante restaurant) throws Exception;
    public String savePlatoEspecial(PlatoEspecial plato) throws Exception;

    //
    public String deleteRacionDia(int rac_id)throws Exception;
    //
    public String deletePlatoEspecial(int plae_id)throws Exception;

    /**
     * el cliente solicita la modificacion de un parametro en la base de datos para plato especial
     * @param plato informacion a modificar
     * @return true si la operacion es exitosa, false si erra
     * @throws Exception 
     */
    public boolean updatePlatoEspecial(PlatoEspecial plato)throws Exception;
    
    /**
     * el cliente solicita la modificacion de un parametro en la base de datos para racion
     * @param racion informacion a moficar
     * @return true si la operacion es exitosa, false si erra
     * @throws Exception 
     */
    public boolean updateRacion(RacionDia racion)throws Exception;
    /**
     * Implementacion que se hara en clienteAccessSocket para el recibimiento 
     * de este tipo por parte del servidor que esta devolviendo una respuesta
     * a la solicitud del cliente de listar menu dia
     * 
     * @param idRes
     * @param diaSem
     * @param resource
     * @return
     * @throws Exception 
     */
    public List<RacionDia> listMenuDay(int idRes,String diaSem,String resource)throws Exception;
    /**
     * Implementacion que se hara en clienteAccessSocket para el recibimiento 
     * de este tipo por parte del servidor que esta devolviendo una respuesta
     * a la solicitud del cliente de listar menu especial
     * 
     * @param idRes
     * @param resource
     * @return
     * @throws Exception 
     */
    public List<PlatoEspecial> listMenuSpecial(int idRes,String resource)throws Exception;


    public String addPedido(Pedido pedido) throws Exception;
    public String addRacionPedido(RacionPed racionPed) throws Exception;
    public String addPlatoEspecialPedido(PlatoEspecialPed platoEspecialPed) throws Exception;

    public String saveRacionDia(RacionDia racion,int idRestaurante) throws Exception;
    public String payedPedido(Pedido pedido) throws Exception;
    public String cancelPedido(Pedido pedido) throws Exception;
    public String deleteRacionPedido(int idRacionPedido) throws Exception;
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido) throws Exception;

     public String validarAcceso (Cliente cliente)throws Exception;



    /**
     * Implementacion que se hara en clienteAccessSocket para el recibimiento 
     * de este tipo por parte del servidor que esta devolviendo una respuesta
     * a la solicitud del cliente de listar menu dia todos
     * 
     * @param idRes
     * @param resource
     * @return
     * @throws Exception 
     */
    public List<RacionDia> listMenuDayAll(int idRes,String resource)throws Exception;
    
    public Restaurante getRestaurante(int id)throws Exception;
    
    public Cliente getClient(int id)throws Exception;
    
    public Recurso getRecuso(String nombre)throws Exception;

    public List<Pedido> listPedido(int idRestaurante)throws Exception;
    
    public List<Restaurante> listRestaurante(String typeRestaurante)throws Exception;
    
    public List<CarritoG> listCarritoRacion(int idCliente, int idPedido,String resource)throws Exception;
    
    public List<CarritoG> listCarritoPlatoEspecial(int idCliente, int idPedido,String resource)throws Exception;
    
    public List<HistorialPed> listHistoryPed(int idCliente, String estado)throws Exception;
    
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception;
    
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception;
    
    public String sumOrder(int idCliente, int idPedido)throws Exception;
    
    public String priceDomicileOrder(int idCliente, int idPedido)throws Exception;
    
    public String impuestoRestaurante(int idCliente, int idPedido)throws Exception;
    
    public String total(int idCliente, int idPedido)throws Exception;
    
    public String registrarCliente(Cliente cliente, String resource)throws Exception;

}
