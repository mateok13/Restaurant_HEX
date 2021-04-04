package co.unicacuca.microkernel.core.dominio;

import co.unicauca.microkernel.common.entities.*;

/**
 * Interface del repositorio de platos, usarla mediante inyeccion de dependencias
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public interface IPlatoRepositorio {
 
    /**
     * almacena un restaurante en la base de datos
     * @param res resgistro a almacenar
     * @return respuesta sobre la operacion
     */
    public String saveRestaurant(Restaurante res);

    /**
     * almacena un plato especialen la base de datos
     * @param plato registro a almacenar
     * @return respuesta sobre la operacion
     */
    public String savePlatoEspecial(PlatoEspecial plato);

    /**
     * hace un update sobre la tabla platoEspecial
     * @param plato informacion a modificar
     * @return 
     */
    public String updatePlatoEspecial(PlatoEspecial plato);
    
    /**
     * hace un update sobre la tabla racion
     * @param racion informacion a modificar
     * @return 
     */
    public String updateRacion(RacionDia racion);

    /**
     * borrar una racion de la base de datos
     * @param rac_id identificaro de la racion
     * @return respuesta de exito o fracaso sobre la operacion
     */
    public String deleteRacionDia(int rac_id);
    /**
     * borrar un plato especial de la base de datos
     * @param plae_id identificador del plato
     * @return respuesta de exito o fracaso sobre la operacion
     */
    public String deletePlatoEspecial(int plae_id);

    /**
     * lista el menu del dia
     * @param idRes identificador del restaurante
     * @param dia
     * @return 
     */
    public String listMenuDay(int idRes,String dia);
    /**
     * lista el menu de platos especiales
     * @param idRes identificador del restaurante
     * @return 
     */
    public String listMenuSpecial(int idRes);
    /**
     * lista todos las raciones o platos del dia de la base de datos
     * @param idRes identificador del restaurante
     * @return 
     */
    public String listMenuDayAll(int idRes);
    /**
     * agrega un pedido
     * @param pedido informacion a registrar
     * @return 
     */
    public String addPedido(Pedido pedido);
    /**
     * agrega un pedido de racion 
     * @param racionPed informacion a registrar
     * @return 
     */
    public String addRacionPedido(RacionPed racionPed);
    /**
     * agrega un pedido de platos especiales
     * @param platoEspecialPed informacion a registrar
     * @return 
     */
    public String addPlatoEspecialPedido(PlatoEspecialPed platoEspecialPed);
    /**
     * almace una racion del dia en la base de datos
     * @param racion informacion a registrar
     * @param idRestaurante identificador del restaurante
     * @return 
     */
    public String saveRacionDia(RacionDia racion,int idRestaurante);
    /**
     * valida la existencia de un cliente y coincidencia de su password
     * @param cliente
     * @return 
     */
    public String validarAcceso(Cliente cliente);
    /**
     * lista los pedidos
     * @param idRestaurante restaurante asociado, identificador
     * @return 
     */
    public String listaPedido(int idRestaurante);
    public String payedPedido(Pedido pedido);
    public String cancelPedido(Pedido pedido);
    public String deleteRacionPedido(int idRacionPedido);
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido);
    public String listRestaurante(String typeRestaurante);
    public String listCarritoRacion(int idCliente, int idPedido); 
    public String listCarritoPlatoEspecial(int idCliente, int idPedido);
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual);
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual);
    public String sumOrder(int idCliente, int idPedido);
    public String priceDomicileOrder(int idCliente, int idPedido);    
    public String impuestoRestaurante(int idCliente, int idPedido);    
    public String total(int idCliente, int idPedido);
    public String listHistoryPed(int idCliente, String estado);
    
    /**
     * Obtiene un restaurante basado en el codigo
     * @param id clave del restaurante a recuperar
     * @return string con la informacion necesaria para construir un restaurante
     */
    public String getRestaurant(int id);
    
    /**
     * Obtiene un ciente basado en el id
     * @param id indentificador para busqueda
     * @return retorna un estring con la informacion necesaria para construir un cliente
     */
    public String getClient(int id);
    
    /**
     * retorna un recurso en bytes
     * @param nombreClave identificador del recurso
     * @return un arreglo de bytes
     */
    public String getRecurso(String nombreClave);
    
    public String registrarCliente(Cliente cliente);
}
