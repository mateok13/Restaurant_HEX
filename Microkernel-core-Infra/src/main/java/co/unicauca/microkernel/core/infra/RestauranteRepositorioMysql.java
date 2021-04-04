package co.unicauca.microkernel.core.infra;

import co.unicacuca.microkernel.core.dominio.IPlatoRepositorio;
import co.unicauca.microkernel.common.entities.*;
import co.unicauca.microkernel.common.infra.Utilities;
import com.google.gson.Gson;
import java.io.File;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * objeto concreto de un repositorio, en este caso un repositorio de mysql
 *
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class RestauranteRepositorioMysql implements IPlatoRepositorio {

    /**
     * Conexión con Mysql
     */
    private Connection conn;
    private DeliveryService deliveryService;
    private Delivery deliveryEntity;

    public RestauranteRepositorioMysql(){
       this.beginDelivery();
    }
    
    private void beginDelivery(){
        try {
            String basePath = getBaseFilePath();
            this.deliveryService = new DeliveryService();
            DeliveryPluginManager.init(basePath);
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean findRacionDia(int id) {
        boolean resultado;
        try {
            this.connect();
            String sql = "select rac_nombre from raciondia where RAC_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            resultado = rs.next();
            ps.close();
            this.disconnect();
            return resultado;
        } catch (SQLException ex) {
            System.out.println("revento excepción encontrar ración: " + ex.getMessage());
            return false;
        }
    }

    private boolean findPlatoEspecial(int id) {
        boolean resultado;
        try {
            this.connect();
            String sql = "select plae_nombre from platoespecial where PLAE_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            resultado = rs.next();
            ps.close();
            this.disconnect();
            return resultado;
        } catch (SQLException ex) {
            System.out.println("revento excepción encontrar plato especial:" + ex.getMessage());
            return false;
        }
    }

    /**
     * actualiza un item plato especial en la base de datos
     *
     * @param plato informacion del plato espeial a modificar
     * @return retorna "FALLO" si el metodo erra
     */
    @Override
    public String updatePlatoEspecial(PlatoEspecial plato){
        if(!this.findPlatoEspecial(plato.getId_pe())){
            return "FALLO";
        }
        try{
            this.connect();
            String sql = "update platoespecial set Mene_id = ?, plae_nombre = ?, plae_foto = ?, plae_descripcion = ?, plae_precio = ? where plae_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plato.getMenuEsp());
            pstmt.setString(2, plato.getNombre());
            pstmt.setBytes(3, plato.getImagen());
            pstmt.setString(4, plato.getDescripcion());
            pstmt.setInt(5, plato.getPrecio());
            pstmt.setInt(6, plato.getId_pe());
            pstmt.executeUpdate();
            
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return plato.getNombre();
    }

    /**
     * actualiza un item de racion en la base de datos.
     *
     * @param racion informacion a modificar
     * @return retorna "FALLO" si erra el metodo, identificador de lo contrario.
     */
    @Override
    public String updateRacion(RacionDia racion){
        if(!this.findRacionDia(racion.getRacId())){
            return "FALLO";
        }
        try{
            this.connect();
            String sql = "update raciondia set mend_id = ?, rac_nombre = ?, rac_foto = ?, rac_tipo = ?, rac_precio = ? where rac_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, racion.getMenuId());
            pstmt.setString(2, racion.getNombre());
            pstmt.setBytes(3, racion.getImagen());
            pstmt.setString(4, racion.getTipo().toString());
            pstmt.setInt(5, racion.getPrecio());
            pstmt.setInt(6, racion.getRacId());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return racion.getNombre();
    }

    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            //estos datos estan quemados en el archivo propertis, si la base de datos cambia propertis debe modificarse
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username"); //usuario de la base de datos
            String pwd = Utilities.loadProperty("server.db.password");//contraseña de usuario
            //se establece la coneccion con los datos previos
            conn = DriverManager.getConnection(url, username, pwd);
            if (conn == null) {
                System.out.println("conexión fallida a la base de datos");
            } else {
                System.out.println("conexión exitosa a la base de datos");
            }
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al consultar de la base de datos", ex);
        }
        return -1;
    }

    /**
     * Cierra la conexion con la base de datos
     *
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.FINER, "Error al cerrar la Conexión", ex);
        }
    }
    private static String getBaseFilePath() {
        try {
            String path = System.getProperty("user.dir");
            File pathFile = new File(path);
            String sep = File.separator;
            path = pathFile.getParent()+sep+"Microkernel-core-Infra"+sep+"src"+sep+"main"+sep+"resources"+sep;
            return path;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String sumOrder(int idCliente, int idPedido) {
        int total = 0;
        int sumaRaciones = 0;
        int sumaPlatosE = 0;

        sumaRaciones = this.sumaRaciones(idCliente, idPedido);
        sumaPlatosE = this.sumaPlatos(idCliente, idPedido);

        total = sumaRaciones + sumaPlatosE;
        return "" + total;
    }
    
    @Override
    public String priceDomicileOrder(int idCliente, int idPedido){
        int sumaOrder = 0;
        double distancia = 0;
        distancia = this.calcularDistancia(idCliente,idPedido);
        String codigo = "";
        codigo = this.codigoRestaurante(idCliente, idPedido);
        int cost = 0;
        try {    
            this.deliveryEntity = new Delivery(sumaOrder, distancia, codigo);
            
            cost = this.deliveryService.calculateDeliveryCostDomicile(this.deliveryEntity);

            return "" + cost;
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, null, ex);
            return "FALLO";
        }
    }    
    
    @Override
    public String impuestoRestaurante(int idCliente, int idPedido){
        int sumaOrder = Integer.parseInt(this.sumOrder(idCliente, idPedido));
        double distancia = 0;
        distancia = this.calcularDistancia(idCliente,idPedido);
        String codigo = "";
        codigo = this.codigoRestaurante(idCliente, idPedido);
        int cost = 0;
        try {    
            this.deliveryEntity = new Delivery(sumaOrder, distancia, codigo);
            
            cost = this.deliveryService.calculateImpuestoRestaurante(this.deliveryEntity);

            return "" + cost;
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, null, ex);
            return "FALLO";
        }
    }     
    
    @Override
    public String total(int idCliente, int idPedido){
        try{
            String domi = this.priceDomicileOrder(idCliente, idPedido);
            String imp = this.impuestoRestaurante(idCliente, idPedido);
            int domicilio = Integer.parseInt(domi);
            int impuesto = Integer.parseInt(imp);
            int total = domicilio+impuesto;        
            return "" + total+"-"+domicilio+"-"+impuesto;
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, null, ex);
            return "FALLO";
        }
        
    }
    @Override
    public String savePlatoEspecial(PlatoEspecial instancia) {
        try{
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "insert into platoespecial (mene_id,plae_nombre,plae_foto,plae_descripcion,plae_precio) values (?,?,?,?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getMenuEsp());
            pstmt.setString(2, instancia.getNombre());
            pstmt.setBytes(3, instancia.getImagen());
            pstmt.setString(4, instancia.getDescripcion());
            pstmt.setInt(5, (int) instancia.getPrecio());
            //se ejecuta la sentencia sql
            //reversar a update por si algo----------------
            pstmt.execute();
            //se cierra
            pstmt.close();
            //se termina la conexión
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        //lo ideal es retornar un id
        return instancia.getNombre();
    }

    @Override
    public String saveRacionDia(RacionDia instancia,int idRestaurante) {
      try{
            //primero se establece la conexión
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "insert into raciondia(mend_id,res_id,rac_nombre,rac_foto,rac_tipo,rac_precio) values (?,?,?,?,?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getMenuId());
            pstmt.setInt(2, idRestaurante);
            pstmt.setString(3, instancia.getNombre());
            pstmt.setBytes(4, instancia.getImagen());
            pstmt.setString(5, instancia.getTipo().toString());
            pstmt.setInt(6, instancia.getPrecio());
            //se ejecuta la sentencia sql
            pstmt.execute();
            //se cierra
            pstmt.close();
            //se termina la coneccion
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        //lo ideal es retornor un id
        return instancia.getNombre();
    }

    /**
     * cumunicacion con la base de datos para eliminar una racion del dia
     *
     * @param rac_id id racion que se desea eliminar
     * @return
     */
    @Override
    public String deleteRacionDia(int rac_id) {
        if (findRacionDia(rac_id)) {
            System.out.println("EXISTE EL ELEMENTO");
        } else {
            System.out.println("NO EXISTE EL ELEMENTO");
            return "FALLO";
        }
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "delete from raciondia where rac_id = (?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se compara el id, OJO Ddebe cumplir estrictamente el orden y el tipo de dato(de las tablas)
            pstmt.setInt(1, rac_id);

            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            //se termina la coneccion
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al eliminar la ración", ex);
        }
        return "" + rac_id;
    }

    @Override
    public String addPedido(Pedido instancia) {
        int resultado = 0;
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "insert into pedido (cli_id,res_id) values (?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getCliente());
            pstmt.setInt(2, instancia.getResId());
            //se ejecuta la sentencia sql
            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            //se termina la conexion
            this.disconnect();
            resultado = getIdPedidoEnd();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return ""+resultado;
    }
    
    private int getIdPedidoEnd(){
        int resultado=0;
        try {
            //primero se establece la conexion
            this.connect();
            //se estructura la sentencia sql en un string
            String sqlR = "select max(ped_id) from pedido";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmtR = conn.prepareStatement(sqlR);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            ResultSet rs = pstmtR.executeQuery();
            while (rs.next()){
                resultado=rs.getInt(1);
            }   
            //se cierra
            pstmtR.close();
            //se desconecta la conexion
            this.disconnect();
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al obtener el registro", ex);
        }
        return resultado;
    }

    /**
     * cumunicacion con la base de datos para eliminar un plato especial
     *
     * @param plae_id id plato que se desea eliminar
     * @return
     */
    @Override
    public String deletePlatoEspecial(int plae_id) {
        if (findPlatoEspecial(plae_id)) {
            System.out.println("EXISTE EL ELEMENTO");
        } else {
            System.out.println("NO EXISTE EL ELEMENTO");
            return "FALLO";
        }
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "delete from platoespecial where plae_id = (?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se compara el id, OJO Ddebe cumplir estrictamente el orden y el tipo de dato(de las tablas)
            pstmt.setInt(1, plae_id);
            //se ejecuta la sentencia sql
            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            //se termina la coneccion
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al eliminar el plato", ex);
        }
        return "" + plae_id;
    }

    @Override
    public String addRacionPedido(RacionPed instancia) {
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "insert into racionpedido (ped_id,rac_id,rac_cantidad) values (?,?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getPedId());
            pstmt.setInt(2, instancia.getRacId());
            pstmt.setInt(3, instancia.getCantidad());         
            //se ejecuta la sentencia sql
            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            //se termina la coneccion
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return null;
    }

    /**
     * Lista el menu por dias desde la consulta hecha a la base de datos añade
     * las tuplas encontradas en una lista las raciones del dia y convierte la
     * lista en json para enviarla por el sockect devuelta al cliente
     *
     * @param idRes
     * @param dia
     * @return
     */
    @Override
    public String listMenuDay(int idRes,String dia) {
        List<RacionDia> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select rac_id,rac_tipo,rac_precio,rac_nombre,mend_id,rac_foto from raciondia where raciondia.mend_id = (select menudia.mend_id from menudia where menudia.res_id = ? and menudia.mend_diasem = ?) and raciondia.res_id = ?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, idRes);
            pstmt.setString(2, dia);
            pstmt.setInt(3, idRes);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                RacionDia pla=  new RacionDia(Integer.parseInt(rs.getString(1)), CategoriaEnum.valueOf(rs.getString(2)), Integer.parseInt(rs.getString(3)), rs.getString(4), Integer.parseInt(rs.getString(5)),rs.getBytes(6));
                list.add(pla);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia", ex);
        }
        return response;
    }

    /**
     * Lista el menu especial desde la consulta hecha a la base de datos añade
     * las tuplas encontradas en una lista de Plato especial y convierte la
     * lista en json para enviarla por el sockect devuelta al cliente
     *
     * @param idRes
     * @return
     */
    @Override
    public String listMenuSpecial(int idRes) {
        List<PlatoEspecial> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select plae_id,m.mene_id,plae_nombre,plae_descripcion,plae_precio,plae_foto"
                    + " from (restaurante r inner join menuespecial m on r.res_id=m.res_id)"
                    + " inner join platoespecial p on m.mene_id=p.mene_id where r.res_id = (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idRes);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {       
                PlatoEspecial pla = new PlatoEspecial(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), Integer.parseInt(rs.getString(5)),rs.getBytes(6));
                list.add(pla);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del especial", ex);
        }
       return response;
    }

    /**
     * Convierte una lista de tipo plato en un json
     *
     * @param list
     * @return
     */
    public String listMenuToJson(List list) {
        Gson gson = new Gson();
        String response = gson.toJson(list);
        return response;
    }
    

    @Override
    public String saveRestaurant(Restaurante res) {
        try {
            this.connect();
            String sql = "insert into restaurante (res_id,res_codigo,res_nombre,res_foto,res_carrera,res_calle) values (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, res.getIdCliente());
            pstmt.setString(2, res.getCodigo());
            pstmt.setString(3, res.getNombre());
            pstmt.setBytes(4, res.getImagen());
            pstmt.setInt(5, res.getCarrera());
            pstmt.setInt(6, res.getCalle());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al guardar el restaurante", ex);
        }
        return res.getNombre();
    }

    @Override
    public String addPlatoEspecialPedido(PlatoEspecialPed instancia) {
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "insert into platoespecialpedido (ped_id,plae_id,plae_cantidad) values (?,?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getPedId());
            pstmt.setInt(2, instancia.getPlaeId());
            pstmt.setInt(3, instancia.getCantidad());
            //se ejecuta la sentencia sql
            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            //se termina la coneccion
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return null;
    }
    @Override
    public String payedPedido(Pedido pedido){
        try{
            this.connect();
            String sql = "update pedido set ped_estado='PAGADO', ped_fecha_pagado=NOW() where ped_id = ? and cli_id = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pedido.getIdPedido());
            pstmt.setInt(2, pedido.getCliente());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return ""+pedido.getIdPedido();
    }
    @Override
    public String cancelPedido(Pedido pedido){
        try{
            this.connect();
            String sql = "update pedido set ped_estado='CANCELADO' where ped_id = ? and cli_id = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pedido.getIdPedido());
            pstmt.setInt(2, pedido.getCliente());

            pstmt.executeUpdate();
            
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return ""+pedido.getIdPedido();
    }
    @Override
    public String deleteRacionPedido(int idRacionPedido){
        try{
            this.connect(); 
            String sql = "delete from racionpedido where racp_id = (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,idRacionPedido);
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al eliminar el plato", ex);
        }
        return ""+idRacionPedido;
    }
    
    @Override
    public String deletePlatoEspecialPedido(int idPlatoEspecialPedido){
        try{
            this.connect(); 
            String sql = "delete from platoespecialpedido where plae_id = (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,idPlatoEspecialPedido);
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al eliminar el plato", ex);
        }
        return ""+idPlatoEspecialPedido;
    }
    
    public int sumaRaciones(int idCliente,int idPedido){
        int sumaRaciones=0;
        try{
            this.connect();
            String sqlRacion
                    = " select sum(racd.rac_precio*racp.rac_cantidad)"
                    + " from pedido ped inner join racionpedido racp on ped.ped_id = racp.ped_id"
                    + " inner join raciondia racd on racp.rac_id = racd.rac_id"
                    + " where ped.cli_id =" + idCliente + " and ped.ped_id =" +idPedido+"";
            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                sumaRaciones = rs1.getInt(1);
            }
            ps1.close();
            this.disconnect();
        } catch (SQLException ex) {
            System.out.println("algo:" + ex.getMessage());
        }
        return sumaRaciones;
    }

    public int sumaPlatos(int idCliente,int idPedido) {
        int sumaPlatos = 0;
        try {
            this.connect();
            String sqlRacion
                    = " select sum(pe.plae_precio*pep.plae_cantidad)"
                    + " from pedido ped inner join platoespecialpedido pep on ped.ped_id=pep.ped_id"
                    + " inner join platoespecial pe on pep.plae_id= pe.plae_id"
                    + " where ped.cli_id =" + idCliente + " and ped.ped_id =" +idPedido+"";
            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                sumaPlatos = rs1.getInt(1);
            }
            ps1.close();
            this.disconnect();
        } catch (SQLException ex) {
            System.out.println("algo:" + ex.getMessage());
        }
        return sumaPlatos;
    }
    
    public double calcularDistancia(int idCliente, int idPedido){
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        double distancia = 0;
        try{
            this.connect();
            String sqlRacion = 
            "select res.res_calle,res.res_carrera,cli.cli_calle,cli.cli_carrera"
            + " from pedido ped inner join restaurante res on ped.res_id=res.res_id"
            + " inner join cliente cli on cli.cli_id=ped.cli_id"
            + " where ped.cli_id = "+idCliente+" and ped.ped_id = "+idPedido+"";
            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                x1 = rs1.getInt(1);
                y1 = rs1.getInt(2);
                x2 = rs1.getInt(3);
                y2 = rs1.getInt(4);
            }
            rs1.close();
            ps1.close();
            this.disconnect();
            distancia=this.calcularDistancia(x1,y1,x2,y2);
        }catch(SQLException ex){
            System.out.println("algo:"+ex.getMessage());
        }
        return distancia;
    }
    
    public double calcularDistancia(int x1, int y1, int x2, int y2){
        int a = (int)Math.pow(x2-x1,2);
        int b = (int)Math.pow(y2-y1,2);
        double distancia = Math.sqrt(a+b);
        return distancia;
    }
    
    public String codigoRestaurante (int idCliente, int idPedido){
        String codigo = "";
        try{
            this.connect();
            String sqlRacion = "select res.res_codigo"
            + " from pedido ped inner join restaurante res on ped.res_id=res.res_id"
            + " where ped.cli_id = "+idCliente+" and ped.ped_id = "+idPedido+"";
            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                codigo = rs1.getString(1);
            }
            rs1.close();
            ps1.close();
            this.disconnect();
        }catch(SQLException ex){
            System.out.println("algo:"+ex.getMessage());
        }
        return ""+codigo;
    }
    
    /**
     * Lista el menu de toda la semana desde la consulta hecha a la base de datos 
     * añade las tuplas encontradas en una lista las raciones de dia
     * y convierte la lista en json para enviarla por el sockect devuelta
     * al cliente
     * @param idRes
     * @return 
     */
    @Override
    public String listMenuDayAll(int idRes) {
        List<RacionDia> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select rac_id,rac_tipo,rac_precio,rac_nombre,mend_id,rac_foto from raciondia where raciondia.res_id = ?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, idRes);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                RacionDia pla=  new RacionDia(Integer.parseInt(rs.getString(1)), CategoriaEnum.valueOf(rs.getString(2)), Integer.parseInt(rs.getString(3)), rs.getString(4), Integer.parseInt(rs.getString(5)),rs.getBytes(6));
                list.add(pla);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia de toda la semana", ex);
        }
        return response;
    }

    @Override
    public String validarAcceso(Cliente cliente) {
        String resultado = "";
        String tipo = "";
        try {
            this.connect();
            String sqlTipo = "select cli_tipo"
                        + " from cliente"
                        + " where cli_nombre like binary '" + cliente.getNombre() + "' and cli_contrasenia like binary '"
                    + cliente.getContrasenia() + "'";
            PreparedStatement pstmtTipo = conn.prepareStatement(sqlTipo);
            ResultSet rsTipo = pstmtTipo.executeQuery();
            
            while (rsTipo.next()){
                tipo = rsTipo.getString(1);
            }
            System.out.println(tipo);
            pstmtTipo.close();
            if (tipo.equals("ADMINISTRADOR")) {
                String sql = "select c.cli_tipo,r.cli_id,r.res_id,r.res_nombre from cliente c inner join restaurante r"
                        + " on c.cli_id=r.cli_id where c.cli_nombre like binary '" + cliente.getNombre() + "' and c.cli_contrasenia like binary '"
                        + cliente.getContrasenia() + "'";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    resultado +=rs1.getString(1)+"-"+rs1.getInt(2)+"-"+rs1.getInt(3)+"-"+rs1.getString(4)+"-";
                }
                pstmt.close();
            }
            if (tipo.equals("COMPRADOR")) {
                String sql = "select cli_tipo,cli_id,cli_contrasenia"
                        + " from cliente"
                        + " where cli_nombre like binary '" + cliente.getNombre() + "' and cli_contrasenia like binary '"
                        + cliente.getContrasenia() + "'";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    resultado +=rs1.getString(1)+"-"+rs1.getInt(2)+"-"+rs1.getString(3)+"-";
                }
                pstmt.close();
            }
            
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al validar usuario", ex);
        }
        return resultado;
    }

    @Override
    public String listaPedido(int idres) {
        String resultado = "";
        List<Pedido> list=new ArrayList<>();
        try {
            this.connect();
            String sql = "select * from pedido where res_id="+idres;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs1 = pstmt.executeQuery();
            while (rs1.next()) {
                Pedido ped=new Pedido(rs1.getInt(1), rs1.getInt(2), rs1.getInt(3), EstadoPed.valueOf(rs1.getString(4)), null,null);
                list.add(ped);
            }
            resultado=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al validar usuario", ex);
        }
        return resultado;
    }
    
    @Override
    public String listRestaurante(String typeRestaurante){
        List<Restaurante> list = new ArrayList<>();
        String response = null;
        try {
            this.connect();
            if (typeRestaurante.equals("Todos")){
                String sql = "select res_id, res_nombre, res_foto, res_calle, res_carrera"
                             + " from restaurante";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Restaurante res = new Restaurante(Integer.parseInt(rs.getString(1)), 99,"", rs.getString(2), rs.getBytes(3), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
                    list.add(res);
                }
                pstmt.close();
            }else{
                String sql = "select res_id, res_nombre, res_foto, res_calle, res_carrera"
                             + " from restaurante"
                             + " where res_codigo like '"+typeRestaurante+"'";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Restaurante res = new Restaurante(Integer.parseInt(rs.getString(1)),99, "", rs.getString(2), rs.getBytes(3), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
                    list.add(res);
                }
                pstmt.close();
            }
            response = listMenuToJson(list);
            System.out.println(response);
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia", ex);
            return "";
        }
        return response;
    }
    
    @Override
    public String listHistoryPed(int idCliente, String estado){
        List<HistorialPed> list = new ArrayList<>();
        String response = null;
        try {
            this.connect();
            String sql = "select p.ped_id, r.res_nombre,p.ped_fecha_creado,p.ped_fecha_pagado"
                    + " from pedido p inner join restaurante r on p.res_id=r.res_id"
                    + " where p.cli_id = " + idCliente + " and p.ped_estado = '" + estado + "'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistorialPed res = new HistorialPed(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(res);
            }
            pstmt.close();
            response = listMenuToJson(list);
            System.out.println(response);
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia", ex);
            return "";
        }
        return response;
    }
    @Override
    public String listCarritoRacion(int idCliente, int idPedido){
        List<CarritoG> list = new ArrayList<>();
        String response = null;
        try {
            this.connect();
            String sql = "select rped.racp_id,rdia.rac_nombre,rdia.rac_precio,rped.rac_cantidad"
                        + " from pedido ped inner join racionpedido rped on ped.ped_id=rped.ped_id"
                        + " inner join raciondia rdia on rdia.rac_id= rped.rac_id"
                        + " where ped.ped_id = "+idPedido+" and ped.cli_id = "+idCliente+"";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CarritoG carritoR = new CarritoG(Integer.parseInt(rs.getString(1)),rs.getString(2), Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4)));
                list.add(carritoR);
            }
            pstmt.close();
            response = listMenuToJson(list);    
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia", ex);
        }
        return response;
    }
    @Override
    public String listCarritoPlatoEspecial(int idCliente, int idPedido){
        List<CarritoG> list = new ArrayList<>();
        String response = null;
        try {
            this.connect();
            String sql = "select pep.plaep_id,pe.plae_nombre,pe.plae_precio,pep.plae_cantidad"
                        + " from pedido ped inner join platoespecialpedido pep on ped.ped_id=pep.ped_id"
                        + " inner join platoespecial pe on pep.plae_id=pe.plae_id"
                        + " where ped.ped_id = "+idPedido+" and ped.cli_id = "+idCliente+"";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CarritoG carritoR = new CarritoG(Integer.parseInt(rs.getString(1)),rs.getString(2), Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4)));
                list.add(carritoR);
            }
            pstmt.close();
            response = listMenuToJson(list);    
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al listar el menu del dia", ex);
        }
        return response;
    }
    
    @Override
    public String aumentarCantidad(String typeOrden,int idOrden, int cantidadActual){
    int cantidad = cantidadActual;
    cantidad++;
        try{
            this.connect();
            if(typeOrden.equals("RACION")){
                String sql = "update racionpedido set rac_cantidad = "+cantidad+" where racp_id = "+idOrden+"";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
            }
            if(typeOrden.equals("PLATO")){
                String sql = "update platoespecialpedido set plae_cantidad = "+cantidad+" where plaep_id = "+idOrden+"";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
            }
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return ""+cantidad;
    }
    @Override
    public String disminuirCantidad(String typeOrden,int idOrden, int cantidadActual){
        int cantidad = cantidadActual;
        try{
            this.connect();
            if (cantidadActual==1){
                if(typeOrden.equals("RACION")){
                    String sql = "delete from racionpedido where racp_id = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                if(typeOrden.equals("PLATO")){
                    String sql = "delete from platoespecialpedido where plaep_id = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }else{
                cantidad--;
                if(typeOrden.equalsIgnoreCase("RACION")){
                    String sql = "update racionpedido set rac_cantidad = "+cantidad+" where racp_id = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                if(typeOrden.equalsIgnoreCase("PLATO")){
                    String sql = "update platoespecialpedido set plae_cantidad = "+cantidad+" where plaep_id = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
            return "FALLO";
        }
        return typeOrden;
    }

    @Override
    public String getRestaurant(int id) {
        List<Restaurante> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select res_id, cli_id, res_codigo, res_nombre, res_foto, res_carrera, res_calle from restaurante where res_id = ?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                Restaurante res= new Restaurante();
                res.setId(rs.getInt(1));
                res.setIdCliente(rs.getInt(2));
                res.setCodigo(rs.getString(3));
                res.setNombre(rs.getString(4));
                res.setImagen(rs.getBytes(5));
                res.setCarrera(rs.getInt(6));
                res.setCalle(rs.getInt(7));
                list.add(res);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al obtener restaurante", ex);
            response = "FALLO";
        }
        return response;
    }

    /**
     * obtiene la informacion correspondiente a un cliente
     * @param id clave con la que se hace la consulta, es el id del cliente
     * @return informacion en formato gson
     */
    @Override
    public String getClient(int id) {
        List<Cliente> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select cli_id, cli_nombre, cli_carrera, cli_calle, cli_foto, cli_tipo from cliente where cli_id = ?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setCarrera(rs.getInt(3));
                cli.setCalle(rs.getInt(4));
                cli.setImagen(rs.getBytes(5));
                cli.setTipo(TipoClien.valueOf(rs.getString(6)));
                list.add(cli);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al obtener cliente", ex);
            response = "FALLO";
        }
        return response;
    }

    /**
     * obtine los recurso en base al nombre
     * @param nombre nombre clave del recurso
     * @return string con la informacion necesario para contruir un recurso
     */
    @Override
    public String getRecurso(String nombre) {
         List<Recurso> list=new ArrayList<>();
        String response=null;
        try{
            this.connect();
            String sql = "select rec_nombre, rec_recurso from recursos where rec_nombre = ?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                Recurso rec = new Recurso();
                rec.setNombre(rs.getString(1));
                rec.setRecurso(rs.getBytes(2));
                list.add(rec);
            }
            response=listMenuToJson(list);
            pstmt.close();
            this.disconnect();
        }catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al obtener recurso", ex);
            response = "FALLO";
        }
        return response;
    }
    
    @Override
    public String registrarCliente(Cliente cliente){
        try {
            this.connect();
            String sql= "insert into cliente (cli_nombre,cli_carrera,cli_calle,cli_tipo,cli_contrasenia,cli_foto) values (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNombre());
            pstmt.setInt(2, cliente.getCarrera());
            pstmt.setInt(3, cliente.getCalle());
            pstmt.setString(4, cliente.getTipo().toString());
            pstmt.setString(5, cliente.getContrasenia());
            pstmt.setBytes(6, cliente.getImagen());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al guardar cliente", ex);
        }
        return cliente.getNombre();
    }
}
