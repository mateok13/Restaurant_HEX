package co.unicauca.microkernel.client.domain;

import co.unicauca.microkernel.client.access.IClienteAccess;
import java.util.List;
import co.unicauca.microkernel.common.entities.*;
import co.unicauca.microkernel.common.infra.Protocol;
import co.unicauca.microkernel.common.infra.Utilities;
import javax.swing.JLabel;

/**
 * servicios que el cliente puede usar del servidor (mascaras)
 * se comunica con la capa de bajo nivel que envia la solicitud
 * @author EdynsonMJ
 * @author Jhonny Rosero
 */
public class ClienteService {
    private final IClienteAccess service;
    /**
     * inyeccion de dependencias
     * @param service un clase concreta que implementa la interfaz de acceso, se instancia con una fabrica
     */
    public ClienteService(IClienteAccess service) {
        this.service = service;
    }
    public String saveRestaurant(Restaurante restaurant) throws Exception{
        return service.saveRestaurant(restaurant);
    }
    public String savePlatoEspecial(PlatoEspecial plato) throws Exception{
        return service.savePlatoEspecial(plato);
    }
    public String saveRacionDia(RacionDia racion,int idRestaurante) throws Exception{
        return service.saveRacionDia(racion,idRestaurante);
    }
    /**
     * el cliente solicita la modificacion de un parametro en la base de datos para plato especial
     * @param plato informacion del plato a actualizar
     * @return true si la operacion es exitosa, false si erra
     * @throws Exception 
     */
    public boolean updatePlatoEspecial(PlatoEspecial plato) throws Exception{
        //validaciones
        return service.updatePlatoEspecial(plato);
    }
    
    /**
     * el cliente solicita la modificacion de un parametro en la base de datos para racion
     * @param racion informacion de la racion a actualizar
     * @return true si la operacion es exitosa, false si erra
     * @throws Exception 
     */
    public boolean updateRacion(RacionDia racion) throws Exception{
        //validaciones
        return service.updateRacion(racion);
    }
    public String deleteRacionDia(int rac_id) throws Exception{
        return service.deleteRacionDia(rac_id);
    }
    public String deletePlatoEspecial(int plae_id) throws Exception{
        return service.deletePlatoEspecial(plae_id);
    }
    public String addPedido(Pedido pedido) throws Exception{
        return service.addPedido(pedido);
    }
    public String addRacionPedido(RacionPed racionPed) throws Exception{
        return service.addRacionPedido(racionPed);
    }
    public String addPlatoEspecialPedido(PlatoEspecialPed platoEspecialPed) throws Exception{
        return service.addPlatoEspecialPedido(platoEspecialPed);
    }     
    public String payedPedido(Pedido pedido) throws Exception{
        return service.payedPedido(pedido);
    }
    public String cancelPedido(Pedido pedido) throws Exception{
        return service.cancelPedido(pedido);
    }
    public String deleteRacionPedido(int idRacionPedido) throws Exception{
        return service.deleteRacionPedido(idRacionPedido);
    }
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido) throws Exception{
        return service.deletePlatoEspecialPedido(idPlatoEspecialPedido);
    }
    public List<RacionDia> listMenuDay(int idRes,String diaSem,String resource)throws Exception{
        return service.listMenuDay(idRes, diaSem, resource);
    }
    public List<PlatoEspecial> listMenuSpecial(int idRes,String resource)throws Exception{
        return service.listMenuSpecial(idRes, resource);
    }
    public List<RacionDia> listMenuDayAll(int idRes,String resource)throws Exception{
        return service.listMenuDayAll(idRes,resource);
    }
    public String validarAcceso (Cliente cliente)throws Exception{
         return service.validarAcceso(cliente);
    }
    public List<Restaurante> listRestaurante(String typeRestaurante)throws Exception{
        return service.listRestaurante(typeRestaurante);
    }
    public List<CarritoG> listCarritoRacion(int idCliente, int idPedido,String resource)throws Exception{
        return service.listCarritoRacion(idCliente, idPedido, resource);
    }
    public List<CarritoG> listCarritoPlatoEspecial(int idCliente, int idPedido,String resource)throws Exception{
        return service.listCarritoPlatoEspecial(idCliente, idPedido, resource);
    }
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception{
        return service.aumentarCantidad(typeOrden, idOrden, cantidadActual);
    }
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception{
        return service.disminuirCantidad(typeOrden, idOrden, cantidadActual);
    }
    public String sumOrder(int idCliente, int idPedido)throws Exception{
        return service.sumOrder(idCliente, idPedido);
    }
    public String priceDomicileOrder(int idCliente, int idPedido)throws Exception{
        return service.priceDomicileOrder(idCliente, idPedido);
    }
    public String impuestoRestaurante(int idCliente, int idPedido)throws Exception{
        return service.impuestoRestaurante(idCliente, idPedido);
    }
    public String total(int idCliente, int idPedido)throws Exception{
        return service.total(idCliente, idPedido);
    }
    public List<HistorialPed> listHistoryPed(int idCliente, String estado)throws Exception{
        return service.listHistoryPed(idCliente, estado);
    }
    public List<Pedido> listPedido(int idres) throws Exception{
        return service.listPedido(idres);
    }
    
    public Restaurante getRestaurante(int id) throws Exception{
        Restaurante aux = service.getRestaurante(id);
        return aux;
    }
    
    public Cliente getClient(int id)throws Exception{
        Cliente aux = service.getClient(id);
        return aux;
    }
    
    public Recurso getRecuso(String nombre)throws Exception{
        Recurso aux = service.getRecuso(nombre);
        return aux;
    }
    
    public void fijarImagen(JLabel refLabel, byte[] imagen, String nombre){
        if (imagen != null) {
            refLabel.setIcon(Utilities.crearIcono(imagen, refLabel.getWidth(), refLabel.getHeight()));
        } else {
            try {
                Recurso aux;
                aux = this.getRecuso(nombre);
                if(aux!=null){
                    refLabel.setIcon(Utilities.crearIcono(aux.getRecurso(), refLabel.getWidth(), refLabel.getHeight()));
                }else{
                    refLabel.setIcon(null);
                }
            } catch (Exception ex) {
                refLabel.setIcon(null);
            }
        }
    }
}
