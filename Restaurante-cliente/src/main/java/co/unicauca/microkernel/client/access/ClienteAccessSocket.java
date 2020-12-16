package co.unicauca.microkernel.client.access;

import co.unicauca.microkernel.client.infra.RestauranteSocket;
import co.unicauca.microkernel.common.entities.*;
import co.unicauca.microkernel.common.infra.JsonError;
import co.unicauca.microkernel.common.infra.Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import static java.lang.String.valueOf;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author EdynsonMJ
 * @author Jhonny Rosero
 */
public class ClienteAccessSocket implements IClienteAccess {

    /**
     * uso de un socket para comunicarse con el servidor
     */
    private RestauranteSocket mySocket;

    //el costructor crea el socket para poder comunicarse con el servidor
    public ClienteAccessSocket() {
        mySocket = new RestauranteSocket();
    }

    /**
     * establece la conexion con el servidor para una solicitud que se pasa por
     * parametro
     *
     * @param requestJson solicitud al servidor
     * @return verdadero si la solicitud es exitosa, false de lo contrario
     * @throws Exception
     */
    private String procesarConexion(String requestJson) throws Exception {
        String jsonResponse = null;
        try {
            //se establece la conexion
            mySocket.connect();
            //se envia la solicitud y se recibe una respuesta,
            //(CREO)AQUI VALIDAR SI SE DIO CON EXITO LA OPERACION, SEGUN LA REPUESTA DEL SERVIDOR
            jsonResponse = mySocket.sendStream(requestJson);
            mySocket.closeStream();
            mySocket.disconnect();
            if (jsonResponse.equals("FALLO")) {
                return "FALLO";
            } else {
                out.println("todo normal");
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
        if (jsonResponse == null) {
            throw new Exception("no se pudo conectar al servidor");
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún erroR, usar mejor login
                out.println("hubo algun tipo de error");
                throw new Exception(this.extractMessages(jsonResponse));
            } else {
                //Devuelve la respuesta del servidor
                return jsonResponse;
            }
        }
    }

    /**
     * Extra los mensajes de la lista de errores
     *
     * @param jsonResponse lista de mensajes json
     * @return Mensajes de error
     */
    private String extractMessages(String jsonResponse) {
        var errors = jsonToErrors(jsonResponse);
        var msjs = "";
        for (var error : errors) {
            msjs += error.getMessage();
        }
        return msjs;
    }

    /**
     * Convierte el jsonError a un array de objetos jsonError
     *
     * @param jsonError
     * @return objeto MyError
     */
    private JsonError[] jsonToErrors(String jsonError) {
        var gson = new Gson();
        var error = gson.fromJson(jsonError, JsonError[].class);
        return error;
    }
    @Override
    public String sumOrder(int idCliente, int idPedido)throws Exception{
        var action = "sumarOrden";
        var requestJson = pluginJson(idCliente,idPedido,action);
        var valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
        return valor;
    }
    
    @Override
    public String priceDomicileOrder(int idCliente, int idPedido)throws Exception{
        var action = "precioDomicilio";
        var requestJson = pluginJson(idCliente,idPedido,action);
        var valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
        return valor;
    }
    
    @Override
    public String impuestoRestaurante(int idCliente, int idPedido)throws Exception{
        var action = "impuestoRestaurante";
        var requestJson = pluginJson(idCliente,idPedido,action);
        var valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
        return valor;
    }
    
    @Override
    public String total(int idCliente, int idPedido)throws Exception{
        var action = "TOTAL";
        var requestJson = pluginJson(idCliente,idPedido,action);
        var valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
        return valor;
    }



    private String pluginJson(int idCliente, int idPedido, String action){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("comprador");
        protocol.setAction(action);
        protocol.addParameter("idCliente", ""+idCliente);
        protocol.addParameter("idPedido", ""+idPedido);
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: " + requestJson);
        return requestJson;
    }

    @Override
    public String savePlatoEspecial(PlatoEspecial instancia) throws Exception {
        String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = crearPlatoEspecialJson(instancia);
        if ((this.procesarConexion(requestJson).equals("FALLO"))) {
            return null;
        }
        return instancia.getNombre();

    }

    /**
     * hace un update sobre la tabla menu especial en la base de datos
     *
     * @param plato informacion del plato a actualizar
     * @return
     */
    @Override
    public boolean updatePlatoEspecial(PlatoEspecial plato) throws Exception {
        //estring en formato json que se enviara al servidor
        var requestJson = updateEspecialJson(plato);
         if(procesarConexion(requestJson).equals("FALLO")){
            out.println("devolvio fallo");
            return false;
        }
        out.println("devolvio ");
        return true;
    }

    /**
     * genera el string en el formato json para ser enviado
     * @param clave
     * @param atributo
     * @param valor
     * @return
     */
    private String updateEspecialJson(PlatoEspecial plato){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("administrador");
        protocol.setAction("updateEspecial");
        protocol.addParameter("clave", "" + plato.getId_pe());
        protocol.addParameter("nombre", plato.getNombre());
        protocol.addParameter("precio", "" + plato.getPrecio());
        protocol.addParameter("descripcion", plato.getDescripcion());
        protocol.addParameter("menu", ""+plato.getMenuEsp());
        protocol.setBytes(plato.getImagen());
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: "+requestJson);
        return requestJson;
    }

    /**
     * solicitud al servidor para hacer update sobre la tabla racion
     *
     * @param racion informacion a modificar
     * @return true operacion exitosa, false de lo contrario
     * @throws Exception
     */

    @Override
    public boolean updateRacion(RacionDia racion) throws Exception{
        var requestJson = updateRacionJson(racion);
        if(procesarConexion(requestJson).equals("FALLO")){
            return false;
        }
        return true;
    }
    /**
     * genera un string con el formato para ser enviado, con la informacion del update racion
     * @param racion objeto a convertir
     * @return
     */
    public String updateRacionJson(RacionDia racion){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("administrador");
        protocol.setAction("updateRacion");
        protocol.addParameter("clave", "" + racion.getRacId());
        protocol.addParameter("nombre", racion.getNombre());
        protocol.addParameter("precio", ""+racion.getPrecio());
        protocol.addParameter("tipo", ""+racion.getTipo());
        protocol.addParameter("dia", ""+racion.getMenuId());
        protocol.setBytes(racion.getImagen());
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: "+requestJson);
        return requestJson;
    }
    
    private String crearPlatoEspecialJson(PlatoEspecial instancia){
        var protocol = new Protocol();
        protocol.setResource("administrador");
        protocol.setAction("postPlatoEspecial");
        protocol.addParameter("mene_id", valueOf(instancia.getMenuEsp()));
        protocol.addParameter("plae_nombre", instancia.getNombre());
        protocol.addParameter("plae_descripcion", instancia.getDescripcion());
        protocol.addParameter("plae_precio", valueOf(instancia.getPrecio()));
        protocol.setBytes(instancia.getImagen());

        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: " + requestJson);
        return requestJson;
    }

    /**
     *
     * @param instancia
     * @return
     * @throws Exception
     */
    @Override
    public String saveRacionDia(RacionDia instancia,int idRestaurante) throws Exception {
        String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = crearRacionDiaJson(instancia,idRestaurante);
        if ((this.procesarConexion(requestJson).equals("FALLO"))) {
            return null;
        }
        return instancia.getNombre();
    }

    private String crearRacionDiaJson(RacionDia instancia,int idRestaurante) {
        var protocol = new Protocol();
        protocol.setResource("administrador");
        protocol.setAction("postRacionDia");
        protocol.addParameter("mend_id", valueOf(instancia.getMenuId()));
        protocol.addParameter("res_id", ""+idRestaurante);
        protocol.addParameter("rac_nombre", instancia.getNombre());
        protocol.addParameter("rac_tipo", instancia.getTipo().toString());
        protocol.addParameter("rac_precio", valueOf(instancia.getPrecio()));
        protocol.setBytes(instancia.getImagen());

        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: " + requestJson);
        return requestJson;
    }

    /**
     *
     * @param rac_id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteRacionDia(int rac_id) throws Exception{
        var respJson = deletePlatoDiaJson(rac_id);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return "" + rac_id;
    }

    /**
     *
     * @param plae_id
     * @return
     * @throws Exception
     */
    @Override
    public String deletePlatoEspecial(int plae_id) throws Exception{
        var respJson = deletePlatoEspecialJson(plae_id);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return "" + plae_id;
    }
    private String deletePlatoDiaJson(int rac_id){
        var protocol = new Protocol();
        protocol.setResource("administrador");
        protocol.setAction("deleteRacionDia");
        protocol.addParameter("rac_id", ""+rac_id);
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);
        
        return requestJson;
    }

    private String deletePlatoEspecialJson(int plae_id){
        var protocol = new Protocol();
        protocol.setResource("administrador");
        protocol.setAction("deletePlatoEspecial");
        protocol.addParameter("plae_id", ""+plae_id);
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;

    }

    @Override
    public String addPedido(Pedido instancia) throws Exception {
        String jsonResponse = null;
        System.out.println("antes de la llamada");
        System.out.println("cli_id:"+instancia.getCliente());
        System.out.println("cli_restaurante:"+instancia.getIdPedido());
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = crearPedido(instancia);
        var response=procesarConexion(requestJson);

        return response;

    }

    private String crearPedido(Pedido instancia){
        var protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("agregarPedido");
        protocol.addParameter("ped_id", String.valueOf(instancia.getIdPedido()));
        protocol.addParameter("cli_id", String.valueOf(instancia.getCliente()));
        protocol.addParameter("res_id", String.valueOf(instancia.getResId()));
        protocol.addParameter("ped_estado", String.valueOf(instancia.getEstado()));
        protocol.addParameter("ped_fecha_creado", String.valueOf(instancia.getFechaCreado()));
        protocol.addParameter("ped_fecha_pagado", String.valueOf(instancia.getFechaPagado()));
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }

    /**
     * Envia el id de un restaurante y devuelve la lista llegada desde el
     * servidor el cual transforma el json recibido desde este en una lista de
     * PLlato dia que conforma un menu por dias
     *
     * @param idRes
     * @param diaSem
     * @param resource
     * @return
     * @throws Exception
     */
    @Override
    public List<RacionDia> listMenuDay(int idRes,String diaSem,String resource) throws Exception {
        var accion="listMenuDay";
        String [] parameters={""+idRes,diaSem};
        var requestJson = createlistMenuJson(resource,accion,parameters);
        var response=procesarConexion(requestJson);
        return jsonListMenuDay(response);
    }

    /**
     * Envia el id de un restaurante y devuelve la lista llegada desde el
     * servidor el cual transforma el json recibido desde este en una lista de
     * PlatoEspecial que conforma un menu especial
     *
     * @param idRes
     * @param resource
     * @return
     * @throws Exception
     */
    @Override
    public List<PlatoEspecial> listMenuSpecial(int idRes,String resource) throws Exception {
        var accion="listMenuSpecial";
        String [] parameters={""+idRes};
        var requestJson = createlistMenuJson(resource,accion,parameters);
        var response= procesarConexion(requestJson);
        return jsonListMenuSpecial(response);
    }

    /**
     * Crea el plato recibido en un json para el envio por el sockect al
     * servidor
     *
     * @param resource
     * @param accion
     * @param resId
     * @return
     */
    private String createlistMenuJson(String resource,String accion,String[] parameters){
        var protocol=new Protocol();
        protocol.setResource(resource);
        protocol.setAction(accion);
        protocol.addParameter("resId", valueOf(parameters[0]));
        if (accion.equals("listMenuDay")) {
            protocol.addParameter("DiaSemana", parameters[1]);
        }
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }

    @Override
    public String addPlatoEspecialPedido(PlatoEspecialPed instancia) throws Exception {
        String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        String requestJson = crearPlatoEspecialPedido(instancia);
        if((this.procesarConexion(requestJson).equals("FALLO"))){
            return null;
        }
        return valueOf(instancia.getPlaepId());

    }
    private String crearPlatoEspecialPedido(PlatoEspecialPed instancia){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("agregarPlatoEspecialPedido");
        protocol.addParameter("plaep_id", String.valueOf(instancia.getPlaepId()));
        protocol.addParameter("ped_id", String.valueOf(instancia.getPedId()));
        protocol.addParameter("plae_id", String.valueOf(instancia.getPlaeId()));
        protocol.addParameter("cantidad", String.valueOf(instancia.getCantidad()));
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);
        return requestJson;
    }
    @Override
    public String payedPedido(Pedido pedido) throws Exception{
        String respJson = payedPedidoJson(pedido);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return ""+pedido;
    }
    private String payedPedidoJson(Pedido pedido){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("payedPedido");
        protocol.addParameter("ped_id", String.valueOf(pedido.getIdPedido()));
        protocol.addParameter("cli_id", String.valueOf(pedido.getCliente()));
        
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;

    }
    @Override
    public String cancelPedido(Pedido pedido) throws Exception{
        String respJson = cancelPedidoJson(pedido);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return ""+pedido;
    }
    private String cancelPedidoJson(Pedido pedido){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("cancelPedido");
        protocol.addParameter("ped_id", String.valueOf(pedido.getIdPedido()));
        protocol.addParameter("cli_id", String.valueOf(pedido.getCliente()));
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;

    }
    @Override
    public String deleteRacionPedido(int idRacionPedido) throws Exception{
        String respJson = deleteRacionPedidoJson(idRacionPedido);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return ""+idRacionPedido;
    }
    private String deleteRacionPedidoJson(int idRacionPedido){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("deleteRacionPedido");
        protocol.addParameter("racp_id", ""+idRacionPedido);
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;

    }
    @Override
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido) throws Exception{
        String respJson = deletePlatoEspecialPedidoJson(idPlatoEspecialPedido);
        if(this.procesarConexion(respJson).equals("FALLO")){
            return "FALLO";
        }
        return ""+idPlatoEspecialPedido;
    }
    private String deletePlatoEspecialPedidoJson(int idPlatoEspecialPedido){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("deletePlatoEspecialPedido");
        protocol.addParameter("plaep_id", ""+idPlatoEspecialPedido);
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;
    }
    
    /**
     * Convierte un json en una lista de tipo plato dia
     * 
     * @param jsonListarMenu
     * @return 
     */
    private List<RacionDia> jsonListMenuDay(String jsonListarMenu){
        var gson=new Gson();
        var list = new TypeToken<List<RacionDia>>(){}.getType();
        return gson.fromJson(jsonListarMenu, list);
    }
    
    /**
     * Convierte un json en una lista de tipo plato especial
     * 
     * @param jsonListarMenu
     * @return 
     */
    private List<PlatoEspecial> jsonListMenuSpecial(String jsonListMenu){
        var gson=new Gson();
        var list = new TypeToken<List<PlatoEspecial>>(){}.getType();
        return gson.fromJson(jsonListMenu, list);
    }

    @Override
    public String saveRestaurant(Restaurante restaurant) throws Exception {
        String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = createRestaurantJson(restaurant);
        if((this.procesarConexion(requestJson).equals("FALLO"))){
            return null;
        }
        return restaurant.getNombre();
    }
    private String createRestaurantJson(Restaurante restaurante){
        var protocol = new Protocol();
        protocol.setResource("administrador");
        protocol.setAction("postRestaurant");
        protocol.addParameter("cli_id", ""+restaurante.getIdCliente());
        protocol.addParameter("res_codigo", restaurante.getCodigo());
        protocol.addParameter("res_nombre", restaurante.getNombre());
        protocol.addParameter("res_foto", Arrays.toString(restaurante.getImagen()));
        protocol.addParameter("res_calle", String.valueOf(restaurante.getCalle()));
        protocol.addParameter("res_carrera", String.valueOf(restaurante.getCarrera()));



        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;
    }
    
    @Override
    public String validarAcceso(Cliente cliente) throws Exception {
        //devuelve un string en formato Json que lo que se enviara
        String requestJson = validardorAcceso(cliente);
        String valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
        return valor;
    }
    
    private String validardorAcceso(Cliente cliente) {
        Protocol protocol = new Protocol();
        protocol.setResource("sistema");
        protocol.setAction("validarAcceso");
        protocol.addParameter("CLI_NOMBRE", String.valueOf(cliente.getNombre()));
        protocol.addParameter("CLI_CONTRASENIA", String.valueOf(cliente.getContrasenia()));
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: " + requestJson);
        return  requestJson;
    }
    
     /**
     * Envia el id de un restaurante y devuelve la lista llegada desde el servidor 
     * el cual transforma el json recibido desde este
     * en una lista de todos los Plato dia que conforma el menu de la semana
     * 
     * @param idRes
     * @param resource
     * @return
     * @throws Exception 
     */
    @Override
    public List<RacionDia> listMenuDayAll(int idRes, String resource) throws Exception {
        String accion="listMenuDayAll";
        String [] parameters={""+idRes};
        String requestJson = createlistMenuJson(resource,accion,parameters);
        String response=procesarConexion(requestJson);
        return jsonListMenuDay(response);
    }
    
    @Override
    public Restaurante getRestaurante(int id) throws Exception {
        var requestJson = getRestauranteJson(id);
        String response = procesarConexion(requestJson);
        if(response.equals("FALLO")){
            out.println("devolvio fallo");
            return null;
        }
        out.println("devolvio ");
        return this.convertJsonRestaurante(response);
    }
     private Restaurante convertJsonRestaurante(String respuestaConsulta){
        var gson=new Gson();
        var list = new TypeToken<List<Restaurante>>(){}.getType();
        List<Restaurante> lista = gson.fromJson(respuestaConsulta, list);
        return lista.get(0);
    }
    private String getRestauranteJson(int id){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("administrador");
        protocol.setAction("getRestaurant");
        protocol.addParameter("clave", "" + id);
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: "+requestJson);
        return requestJson;
    }
    


    @Override
    public Cliente getClient(int id)throws Exception {
        var requestJson = getClientJson(id);
        String response = procesarConexion(requestJson);
        if(response.equals("FALLO")){
            out.println("devolvio fallo");
            return null;
        }
        out.println("devolvio ");
        return this.convertJsonClient(response);
    }
    
    private String getClientJson(int id){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("administrador");
        protocol.setAction("getClient");
        protocol.addParameter("clave", "" + id);
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: "+requestJson);
        return requestJson;
    }
    
    private Cliente convertJsonClient(String respuestaConsulta){
        var gson=new Gson();
        var list = new TypeToken<List<Cliente>>(){}.getType();
        List<Cliente> lista = gson.fromJson(respuestaConsulta, list);
        return lista.get(0);
    }

      @Override
    public Recurso getRecuso(String nombre) throws Exception {
        var requestJson = getRecursoJson(nombre);
        String response = procesarConexion(requestJson);
        if(response.equals("FALLO")){
            out.println("devolvio fallo");
            return null;
        }
        out.println("devolvio ");
        return this.convertJsonRecurso(response);
    }
    private String getRecursoJson(String nombre){
        var protocol = new Protocol();
        //el orden debe ser respetado
        protocol.setResource("sistema");
        protocol.setAction("getRecurso");
        protocol.addParameter("clave", "" + nombre);
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json enviado: "+requestJson);
        return requestJson;
    }
      
     private Recurso convertJsonRecurso(String respuestaConsulta){
        var gson=new Gson();
        var list = new TypeToken<List<Recurso>>(){}.getType();
        List<Recurso> lista = gson.fromJson(respuestaConsulta, list);
        return lista.get(0);
    }
    
    @Override
    public List<Pedido> listPedido(int idRestaurante) throws Exception {
        String accion="listPedido";
        String [] parameters={""+idRestaurante};
        String requestJson = createlistJson("administrador",accion,parameters);
        String response=procesarConexion(requestJson);
        return jsonList(response);
    }
    private String createlistJson(String resource,String accion,String[] parameters){
        var protocol=new Protocol();
        protocol.setResource(resource);
        protocol.setAction(accion);
        protocol.addParameter("resId", valueOf(parameters[0]));
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }
    public String addRacionPedido(RacionPed instancia) throws Exception {
        String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = crearRacionPedido(instancia);
        if((this.procesarConexion(requestJson).equals("FALLO"))){
            return null;
        }
        return valueOf(instancia.getRacpId());

    }
    private String crearRacionPedido(RacionPed instancia){
        var protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("agregarRacionPedido");
        protocol.addParameter("racp_id", String.valueOf(instancia.getRacpId()));
        protocol.addParameter("ped_id", String.valueOf(instancia.getPedId()));
        protocol.addParameter("rac_id", String.valueOf(instancia.getRacId()));
        protocol.addParameter("cantidad", String.valueOf(instancia.getCantidad()));
        

        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);
        return requestJson;
    }

    @Override
    public List<Restaurante> listRestaurante(String typeRestaurante)throws Exception{
    String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = listRestauranteJson(typeRestaurante);
        var response = this.procesarConexion(requestJson);
        
        return jsonListRestaurante(response);

    }

    private String listRestauranteJson(String typeRestaurante){
        var protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("listarRestaurantes");
        
        protocol.addParameter("tipo_restaurante", typeRestaurante);
        
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }
    private List<Restaurante> jsonListRestaurante(String jsonRestaurante){
        var gson=new Gson();
        var list = new TypeToken<List<Restaurante>>(){}.getType();
        return gson.fromJson(jsonRestaurante, list);
        
    }
    @Override
    public List<HistorialPed> listHistoryPed(int idCliente, String estado)throws Exception{
    String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = listHistoriaPedJson(idCliente, estado);
        var response = this.procesarConexion(requestJson);
        
        return jsonListHistoria(response);

    }

    private String listHistoriaPedJson(int idCliente, String estado){
        var protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction("listarHistoria");
        
        protocol.addParameter("idCliente", ""+idCliente);
        protocol.addParameter("estado_pedido", estado);
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }
    private List<HistorialPed> jsonListHistoria(String jsonHistoria){
        var gson=new Gson();
        var list = new TypeToken<List<HistorialPed>>(){}.getType();
        return gson.fromJson(jsonHistoria, list);
        
    }
    @Override
    public List<CarritoG> listCarritoRacion(int idCliente, int idPedido,String resource)throws Exception{
    String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = listCarritoRacionJson(idCliente,idPedido,resource);
        var response = this.procesarConexion(requestJson);
        
        return jsonListCarritoG(response);

    }

    private String listCarritoRacionJson(int idCliente, int idPedido,String resource){
        var protocol = new Protocol();
        protocol.setResource(resource);
        protocol.setAction("listarCarritoRacion");
        
        protocol.addParameter("cliente_id", ""+idCliente);
        protocol.addParameter("pedido_id", ""+idPedido);
        
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);
        
        return requestJson;
    }
    private List<Pedido> jsonList(String jsonListar){
        var gson=new Gson();
        var list = new TypeToken<List<Pedido>>(){}.getType();
        return gson.fromJson(jsonListar, list);
    }

    @Override
    public List<CarritoG> listCarritoPlatoEspecial(int idCliente, int idPedido, String resource)throws Exception{
    String jsonResponse = null;
        //devuelve un string en formato Json que lo que se enviara
        var requestJson = listCarritoPlatoEspecialJson(idCliente,idPedido,resource);
        var response = this.procesarConexion(requestJson);
        
        return jsonListCarritoG(response);

    }

    private String listCarritoPlatoEspecialJson(int idCliente, int idPedido, String resource){
        var protocol = new Protocol();
        protocol.setResource(resource);
        protocol.setAction("listarCarritoPlatoEspecial");
        
        protocol.addParameter("cliente_id", ""+idCliente);
        protocol.addParameter("pedido_id", ""+idPedido);
        
        
        var gson = new Gson();
        var requestJson = gson.toJson(protocol);
        out.println("json: "+requestJson);

        return requestJson;
    }
    private List<CarritoG> jsonListCarritoG(String jsonRestaurante){
        var gson=new Gson();
        var list = new TypeToken<List<CarritoG>>(){}.getType();
        return gson.fromJson(jsonRestaurante, list);
    }

    @Override
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception{
        String action = "aumentarCantidad";
        String requestJson = CantidadJson(typeOrden, idOrden, cantidadActual, action);
        String valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
       return valor;
    }

    @Override
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual) throws Exception{
        String action = "disminuirCantidad";
        String requestJson = CantidadJson(typeOrden, idOrden, cantidadActual,action);
        String valor = this.procesarConexion(requestJson);
        if (valor.equals("FALLO")) {
            return null;
        }
       return valor;
    }
    private String CantidadJson(String typeOrden,int idOrden, int cantidadActual, String action){
        Protocol protocol = new Protocol();
        protocol.setResource("comprador");
        protocol.setAction(action);
        protocol.addParameter("type_orden", typeOrden);
        protocol.addParameter("id_orden", ""+idOrden);
        protocol.addParameter("cantidad_actual", ""+cantidadActual);
        
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        System.out.println("json: "+requestJson);

        return requestJson;
    }

}
