package co.unicacuca.microkernel.core.dominio;

import co.unicauca.microkernel.common.entities.*;


/**
 * Comunicación con la capa de bajo nivel
 * metodos contra la base de datos
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class PlatoServicio {
    /**
     * repositorio de platos, via de comunicacion a bajo nivel
     */
    private final IPlatoRepositorio repositorio;
    
    public PlatoServicio(IPlatoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public String savePlatoEspecial(PlatoEspecial plato){
        //hacer validaciones aqui
        return repositorio.savePlatoEspecial(plato);
    }


    /**
     * modifica un plato especial en la base de datos
     * @param plato informacion del plato a modificar
     * @return retorna "FALLO" en caso en caso de errar
     */
    public String updatePlatoEspecial(PlatoEspecial plato){
        //hacer validaciones, conversion del valor
        return repositorio.updatePlatoEspecial(plato);
    }
    
    /**
     * modifica una racion en la base de datos
     * @param racion informacion a modificar
     * @return retorno "FALLO" en caso de error
     */
    public String updateRacion(RacionDia racion){
        //hacer validaciones, conversion del valor
        return repositorio.updateRacion(racion);
    }

    /**
     * borra una racion del sitema segun un identificador
     * @param rac_id identificador de la racion
     * @return respuesta de la operacion
     */
    public String deleteRacionDia(int rac_id) {
        return repositorio.deleteRacionDia(rac_id);
    }
    /**
     * elimina un plato especial 
     * @param plae_id identificador del plato
     * @return respuesta del la operacion
     */
    public String deletePlatoEspecial(int plae_id) {
        return repositorio.deletePlatoEspecial(plae_id);
    }

    /**
     * guarda un retaurante en la base de datos
     * @param restaurant instancia a registrar
     * @return respuesta sobre la operacion
     */
    public String saveRestaurant(Restaurante restaurant){
        return repositorio.saveRestaurant(restaurant);
    }
    /**
     * lista el menu de raciones
     * @param resId identificador de un restaurante al que esta asociada la racion
     * @param dia dia que tiene asignada la raicon
     * @return respuesta sobre la operacion
     */
    public String listMenuDay(int resId,String dia){
        return repositorio.listMenuDay(resId,dia);
    }
    /**
     * listar menu especial
     * @param resId identificador del restaurante
     * @return FALLO  en caso de fracasar
     */
    public String listMenuSpecial(int resId){
        return repositorio.listMenuSpecial(resId);
    }
    /**
     * adiciona un pedido 
     * @param pedido registro a guardar
     * @return 
     */
    public String addPedido(Pedido pedido){
        return repositorio.addPedido(pedido);
    }
    /**
     * 
     * @param racionPed
     * @return 
     */
    public String addRacionPedido(RacionPed racionPed){
        return repositorio.addRacionPedido(racionPed);
    }
    /**
     * 
     * @param platoEspecialPed
     * @return 
     */
    public String addPlatoEspecialPedido(PlatoEspecialPed platoEspecialPed){
        return repositorio.addPlatoEspecialPedido(platoEspecialPed);
    }
    /**
     * guarda una racion en la base de datos
     * @param racion registro a guardar
     * @param idRestaurante identificador del restaurante al que pertenece
     * @return 
     */
    public String saveRacionDia(RacionDia racion,int idRestaurante){
        return repositorio.saveRacionDia(racion,idRestaurante);
    }
    public String payedPedido(Pedido pedido){
        return repositorio.payedPedido(pedido);
    }
    public String cancelPedido(Pedido pedido){
        return repositorio.cancelPedido(pedido);
    }
    public String deleteRacionPedido(int idRacionPedido){
        return repositorio.deleteRacionPedido(idRacionPedido);
    }
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido){
        return repositorio.deletePlatoEspecialPedido(idPlatoEspecialPedido);
    }

    /**
     * verifica la cuenta de un cliente
     *
     * @param cliente datos de un cliente
     * @return
     */
    public String validarAcceso(Cliente cliente) {
        return repositorio.validarAcceso(cliente);
    }

    /**
     * obtiene la lista de raciones para todos los dias
     * @param resId identificador del restaurante
     * @return 
     */
    public String listMenuDayAll(int resId){
        return repositorio.listMenuDayAll(resId);
    }
    /**
     * cosulta la exitencia de un restaurante
     * @param id identificador de restaurante
     * @return informacion relrestaurante o FALLLO  en caso de error
     */
    public String getRestaurant(int id){
        return repositorio.getRestaurant(id);
    }
    
    /**
     * cunsulta la existencia de un cliente
     * @param id identificador del cliente
     * @return informacion del cliente  o FALLO en caso de error
     */
    public String getCliente(int id){
        return repositorio.getClient(id);
    }
    
    /**
     * consulta la base de dato spor un recurso solicitado
     * @param nombre nombre clave del recurso
     * @return la informacion del recurso.
     */
    public String getRecurso(String nombre){
        return repositorio.getRecurso(nombre);
    }
    
    /**
     * lista pedidos
     * @param idRestaurante identificador del restaurante
     * @return lista almacenadda en string
     */
    public String listPedido(int idRestaurante){
        return repositorio.listaPedido(idRestaurante);
    }
    
    /**
     * lista restaurantes
     * @param typeRestaurante tipo de restaurante a listar
     * @return lista en un string
     */
    public String listRestaurante(String typeRestaurante){
        return repositorio.listRestaurante(typeRestaurante);
    }
    public String listCarritoRacion(int idCliente, int idPedido){
        return repositorio.listCarritoRacion(idCliente, idPedido);
    } 
    public String listCarritoPlatoEspecial(int idCliente, int idPedido){
        return repositorio.listCarritoPlatoEspecial(idCliente, idPedido);
    }
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual){
        return repositorio.aumentarCantidad(typeOrden, idOrden, cantidadActual);
    }
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual){
        return repositorio.disminuirCantidad(typeOrden, idOrden, cantidadActual);
    }
    public String sumOrder(int idCliente, int idPedido){
        return repositorio.sumOrder(idCliente, idPedido);
    }
    public String priceDomicileOrder(int idCliente, int idPedido){
        return repositorio.priceDomicileOrder(idCliente, idPedido);
    }
    public String impuestoRestaurante(int idCliente, int idPedido){
        return repositorio.impuestoRestaurante(idCliente, idPedido);
    }   
    public String total(int idCliente, int idPedido){
        return repositorio.total(idCliente, idPedido);
    }
    public String listHistoryPed(int idCliente, String estado){
        return repositorio.listHistoryPed(idCliente, estado);
    }
    public String registrarCliente(Cliente cliente){
        return repositorio.registrarCliente(cliente);
    }
}