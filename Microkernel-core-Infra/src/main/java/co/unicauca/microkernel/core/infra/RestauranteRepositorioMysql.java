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
 * @author EdynsonMJ
 * @author Jhonny Rosero
 * @author jhonfer ruiz
 * @author camilo gonzales
 * @author james silva
 */
//AUTO_INCREMENT
public class RestauranteRepositorioMysql implements IPlatoRepositorio {

    /**
     * Conección con Mysql
     */
    private Connection conn;

    public RestauranteRepositorioMysql() {

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
            System.out.println("revento excepcion encontrar racion_:" + ex.getMessage());
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
            System.out.println("revento excepcion encontrar plato especial_:" + ex.getMessage());
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
            String sql = "UPDATE platoespecial SET MENE_ID = ?, PLAE_NOMBRE = ?, PLAE_FOTO = ?, PLAE_DESCRIPCION = ?, PLAE_PRECIO = ? WHERE PLAE_ID = ?";
            //String sql = "UPDATE platoespecial SET "+atributo+" = ? WHERE PLAE_ID = ?";
            System.out.println("SENTENCIA SQL UPDATE PLATO ESPECIAL: "+sql);
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
            //String sql = "UPDATE platoespecial set "+atributo+" = "+valor+" WHERE PESP_NOMBRE = "+clave;
            String sql = "UPDATE raciondia SET MEND_ID = ?, RAC_NOMBRE = ?, RAC_FOTO = ?, RAC_TIPO = ?, RAC_PRECIO = ? WHERE RAC_ID = ?";
            System.out.println("SENTENCIA SQL UPDATE RACION: "+sql);
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
                System.out.println("coneccion fallida a la base de datos");
            } else {
                System.out.println("conecion exitosa a la base de datos");
            }
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
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
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
    private static String getBaseFilePath() {
        try {
            String path = RestauranteRepositorioMysql.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = URLDecoder.decode(path, "UTF-8"); //This should solve the problem with spaces and special characters
            File pathFile = new File(path);
            if (pathFile.isFile()) {
                path = pathFile.getParent();
                if (!path.endsWith(File.separator)){
                    path += File.separator;
                }
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String sumOrder(int idCliente, int idPedido){
        int total =0;
        int sumaRaciones = 0;
        int sumaPlatosE = 0;
        
            sumaRaciones = this.sumaRaciones(idCliente,idPedido);
            sumaPlatosE = this.sumaPlatos(idCliente,idPedido);
            
            total = sumaRaciones+sumaPlatosE;
        return ""+total;
    }
    
    @Override
    public String priceDomicileOrder(int idCliente, int idPedido){
        int sumaOrder = Integer.parseInt(this.sumOrder(idCliente, idPedido));
        double distancia = 0;
        distancia = this.calcularDistancia(idCliente,idPedido);
        String codigo = "";
        codigo = this.codigoRestaurante(idCliente, idPedido);
        int cost = 0;
        try {    
            String basePath = getBaseFilePath();
            DeliveryService deliveryService = new DeliveryService();

            DeliveryPluginManager.init(basePath);
            Delivery deliveryEntity = new Delivery(sumaOrder, distancia, codigo);
            
            cost = deliveryService.calculateDeliveryCostDomicile(deliveryEntity);

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
            String basePath = getBaseFilePath();
            DeliveryService deliveryService = new DeliveryService();

            DeliveryPluginManager.init(basePath);
            Delivery deliveryEntity = new Delivery(sumaOrder, distancia, codigo);
            
            cost = deliveryService.calculateImpuestoRestaurante(deliveryEntity);

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
            System.out.println("AQUIIIIIIIIIIIIIII");
            System.out.println(total+"564asd");
        
            return ""+total;
        } catch (Exception ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, null, ex);
            return "FALLO";
        }
        
    }
    @Override
    public String savePlatoEspecial(PlatoEspecial instancia) {
        try{
 //           if (findPlatoEspecial(instancia.getId_pe()))
 //           {
//                return "FALLO";
//            }
            System.out.println("entro");
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "INSERT INTO platoespecial (MENE_ID,PLAE_NOMBRE,PLAE_FOTO,PLAE_DESCRIPCION,PLAE_PRECIO) VALUES (?,?,?,?,?)";
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
            //se termina la coneccion
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        //lo ideal es retornor un id
        return instancia.getNombre();
    }

    @Override
    public String saveRacionDia(RacionDia instancia,int idRestaurante) {
      try{
       //    if (findRacion(instancia.getRacId()))
 //           {
//                return "FALLO";
//            }
            System.out.println("entro");
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "INSERT INTO raciondia(MEND_ID,RES_ID,RAC_NOMBRE,RAC_FOTO,RAC_TIPO,RAC_PRECIO) VALUES (?,?,?,?,?,?)";
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
            String sql = "DELETE FROM raciondia WHERE rac_id = (?)";
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
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al eliminar la racion", ex);
        }
        return "" + rac_id;
    }

    @Override
    public String addPedido(Pedido instancia) {
        int resultado = 0;
        try {
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            System.out.println("id restaurante = "+instancia.getResId());
            //se estructura la sentencia sql en un string
            String sql = "INSERT INTO pedido (CLI_ID,RES_ID) VALUES (?,?)";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            pstmt.setInt(1, instancia.getCliente());
            pstmt.setInt(2, instancia.getResId());
            

            //se ejecuta la sentencia sql
            pstmt.executeUpdate();
            //se cierra
            pstmt.close();
            String sqlR = "(SELECT MAX(PED_ID) FROM pedido )";
            //pstmt mantendra la solicitud sobre la base de datos, se asignam sus columnas
            PreparedStatement pstmtR = conn.prepareStatement(sqlR);
            //se registra cada elemento, OJO Ddebe cumplir estrictamente el orden y el tipo de dato
            ResultSet rs = pstmtR.executeQuery();
            while (rs.next()){
                resultado=rs.getInt(1);
            }
            
            //se cierra
            pstmtR.close();
            //se termina la coneccion
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);

        }
        return ""+resultado;
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
            String sql = "DELETE FROM platoespecial WHERE plae_id = (?)";
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
            System.out.println("entro a agregar pedido");
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "INSERT INTO racionpedido (PED_ID,RAC_ID,RAC_CANTIDAD) VALUES (?,?,?)";
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
        System.out.println("Entered the list menu day");
        try{
            this.connect();
            String sql = "SELECT rac_id,rac_tipo,rac_precio,rac_nombre,mend_id,rac_foto FROM raciondia where raciondia.MEND_ID = (SELECT menudia.MEND_ID FROM menudia WHERE menudia.RES_ID = ? and menudia.MEND_DIASEM = ?) and raciondia.RES_ID = ?";
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
        System.out.println("Entered the list menu Special");
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
        System.out.println("Entered the save restaurant");
        try {
            this.connect();

            String sql = "INSERT INTO restaurante (RES_ID,RES_CODIGO,RES_NOMBRE,RES_FOTO,RES_CARRERA,RES_CALLE) values (?,?,?,?,?,?)";

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
            System.out.println("entro");
            //primero se establece la conexion
            this.connect(); //validar cuando la conexion no sea exitosa
            //se estructura la sentencia sql en un string
            String sql = "INSERT INTO platoespecialpedido (PED_ID,PLAE_ID,PLAE_CANTIDAD) VALUES (?,?,?)";
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
            //String sql = "UPDATE platoespecial set "+atributo+" = "+valor+" WHERE PESP_NOMBRE = "+clave;
            String sql = "UPDATE pedido SET PED_ESTADO='PAGADO', PED_FECHA_PAGADO=NOW() WHERE PED_ID = ? AND CLI_ID = ? ";
            System.out.println("SENTENCIA SQL UPDATE RACION: "+sql);
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
            //String sql = "UPDATE platoespecial set "+atributo+" = "+valor+" WHERE PESP_NOMBRE = "+clave;
            String sql = "UPDATE pedido SET PED_ESTADO='CANCELADO' WHERE PED_ID = ? AND CLI_ID = ? ";
            System.out.println("SENTENCIA SQL UPDATE RACION: "+sql);
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
            String sql = "DELETE FROM racionpedido WHERE RACP_ID = (?)";
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
            String sql = "DELETE FROM platoespecialpedido WHERE plae_id = (?)";
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
                    = " SELECT sum(racd.RAC_PRECIO*racp.RAC_CANTIDAD)"
                    + " from pedido ped INNER JOIN racionpedido racp on ped.PED_ID = racp.PED_ID"
                    + " INNER JOIN raciondia racd on racp.RAC_ID = racd.RAC_ID"
                    + " where ped.CLI_ID =" + idCliente + " AND ped.PED_ID =" +idPedido+"";

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
        System.out.println("Las raciones dan = "+sumaRaciones);
        return sumaRaciones;
    }

    public int sumaPlatos(int idCliente,int idPedido) {
        int sumaPlatos = 0;
        try {
            this.connect();
            String sqlRacion
                    = " SELECT sum(pe.PLAE_PRECIO*pep.PLAE_CANTIDAD)"
                    + " FROM pedido ped INNER JOIN platoespecialpedido pep on ped.PED_ID=pep.PED_ID"
                    + " INNER JOIN platoespecial pe on pep.PLAE_ID= pe.PLAE_ID"
                    + " where ped.CLI_ID =" + idCliente + " AND ped.PED_ID =" +idPedido+"";

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
        System.out.println("Las platos especiales dan = "+sumaPlatos);
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
            "SELECT res.RES_CALLE,res.RES_CARRERA,cli.CLI_CALLE,cli.CLI_CARRERA"
            + " FROM pedido ped INNER JOIN restaurante res on ped.RES_ID=res.RES_ID"
            + " INNER JOIN cliente cli on cli.CLI_ID=ped.CLI_ID"
            + " WHERE ped.CLI_ID = "+idCliente+" AND ped.PED_ID = "+idPedido+"";

            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
                x1 = rs1.getInt(1);
                y1 = rs1.getInt(2);
                x2 = rs1.getInt(3);
                y2 = rs1.getInt(4);
            }
            distancia=this.calcularDistancia(x1,y1,x2,y2);

            ps1.close();
            this.disconnect();
 
        }catch(SQLException ex){
            System.out.println("algo:"+ex.getMessage());

        }
        System.out.println("Las distancia da = "+distancia);
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
            String sqlRacion = 
            "SELECT res.RES_CODIGO"
            + " FROM pedido ped INNER JOIN restaurante res on ped.RES_ID=res.RES_ID"
            + " WHERE ped.CLI_ID = "+idCliente+" AND ped.PED_ID = "+idPedido+"";

            PreparedStatement ps1 = conn.prepareStatement(sqlRacion);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
                codigo = rs1.getString(1);
            }

            ps1.close();
            this.disconnect();
 
        }catch(SQLException ex){
            System.out.println("algo:"+ex.getMessage());

        }
        System.out.println("el codigo de restaurante da = "+codigo);
        return codigo;

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
        System.out.println("Entered the list menu day all");
        try{
            this.connect();
            String sql = "SELECT rac_id,rac_tipo,rac_precio,rac_nombre,mend_id,rac_foto FROM raciondia where raciondia.RES_ID = ?";
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
        System.out.println("NOMBRE: "+cliente.getNombre());
        System.out.println("con: "+cliente.getContrasenia());
        try {
            this.connect();
            String sqlTipo = "SELECT CLI_TIPO"
                        + " from cliente"
                        + " where CLI_NOMBRE LIKE BINARY '" + cliente.getNombre() + "' and CLI_CONTRASENIA LIKE BINARY '"
                    + cliente.getContrasenia() + "'";
            PreparedStatement pstmtTipo = conn.prepareStatement(sqlTipo);
            ResultSet rsTipo = pstmtTipo.executeQuery();
            
            while (rsTipo.next()){
                tipo = rsTipo.getString(1);
            }
            System.out.println(tipo);
            pstmtTipo.close();
            if (tipo.equals("ADMINISTRADOR")) {
                String sql = "Select c.CLI_TIPO,r.RES_ID,r.RES_NOMBRE from cliente c inner join restaurante r"
                        + " on c.CLI_ID=r.CLI_ID where c.CLI_NOMBRE LIKE BINARY '" + cliente.getNombre() + "' and c.CLI_CONTRASENIA LIKE BINARY '"
                        + cliente.getContrasenia() + "'";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    resultado +=rs1.getString(1)+"-"+rs1.getInt(2)+"-"+rs1.getString(3)+"-";
                }
                pstmt.close();
            }
            if (tipo.equals("COMPRADOR")) {
                String sql = "Select CLI_TIPO,CLI_ID,CLI_CONTRASENIA"
                        + " from cliente"
                        + " where CLI_NOMBRE LIKE BINARY '" + cliente.getNombre() + "' and CLI_CONTRASENIA LIKE BINARY '"
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
        //
        String resultado = "";
        List<Pedido> list=new ArrayList<>();
        try {
            this.connect();
            String sql = "select * from pedido where RES_ID="+idres;
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
    
    public String listRestaurante(String typeRestaurante){
        List<Restaurante> list = new ArrayList<>();
        String response = null;
        System.out.println("Entered the list menu day");
        try {
            this.connect();
            if (typeRestaurante.equals("Todos")){
                String sql = "SELECT RES_ID, RES_NOMBRE, RES_FOTO, RES_CALLE, RES_CARRERA"
                             + " from restaurante";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Restaurante res = new Restaurante(Integer.parseInt(rs.getString(1)), 99,"", rs.getString(2), rs.getBytes(3), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
                    list.add(res);
                    System.out.println("ALGO");
                    System.out.println(list.size());
                }
                pstmt.close();
            }else{
                String sql = "SELECT RES_ID, RES_NOMBRE, RES_FOTO, RES_CALLE, RES_CARRERA"
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
        System.out.println("Entered the list menu day");
        try {
            this.connect();
                String sql = " SELECT  p.PED_ID, r.RES_NOMBRE,p.PED_FECHA_CREADO,p.PED_FECHA_PAGADO"
                        +" FROM pedido p inner join restaurante r on p.RES_ID=r.RES_ID" 
                        +" WHERE p.CLI_ID = "+idCliente+" and p.PED_ESTADO = '"+estado+"'";
                PreparedStatement pstmt=conn.prepareStatement(sql);
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
        System.out.println("Entered the list menu day");
        try {
            this.connect();
            String sql = "SELECT rped.RACP_ID,rdia.RAC_NOMBRE,rdia.RAC_PRECIO,rped.RAC_CANTIDAD"
                        + " FROM pedido ped inner join racionpedido rped on ped.PED_ID=rped.PED_ID"
                        + " inner join raciondia rdia on rdia.RAC_ID= rped.RAC_ID"
                        + " WHERE ped.PED_ID = "+idPedido+" and ped.CLI_ID = "+idCliente+"";
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
        System.out.println("Entered the list menu day");
        try {
            this.connect();
            String sql = "SELECT pep.PLAEP_ID,pe.PLAE_NOMBRE,pe.PLAE_PRECIO,pep.PLAE_CANTIDAD"
                        + " FROM pedido ped inner join platoespecialpedido pep on ped.PED_ID=pep.PED_ID"
                        + " inner join platoespecial pe on pep.PLAE_ID=pe.PLAE_ID"
                        + " WHERE ped.PED_ID = "+idPedido+" and ped.CLI_ID = "+idCliente+"";
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
        System.out.println("la cantidad aumentada es: "+cantidad);
        try{
            this.connect();
            if(typeOrden.equals("RACION")){
                String sql = "UPDATE racionpedido SET RAC_CANTIDAD = "+cantidad+" WHERE RACP_ID = "+idOrden+"";
                PreparedStatement pstmt=conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
            }
            if(typeOrden.equals("PLATO")){
                String sql = "UPDATE platoespecialpedido SET PLAE_CANTIDAD = "+cantidad+" WHERE PLAEP_ID = "+idOrden+"";
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
                    String sql = "DELETE FROM racionpedido WHERE RACP_ID = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                if(typeOrden.equals("PLATO")){
                    String sql = "DELETE FROM platoespecialpedido WHERE PLAEP_ID = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }else{
                cantidad--;
                if(typeOrden.equalsIgnoreCase("RACION")){
                    String sql = "UPDATE racionpedido SET RAC_CANTIDAD = "+cantidad+" WHERE RACP_ID = "+idOrden+"";
                    PreparedStatement pstmt=conn.prepareStatement(sql);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                if(typeOrden.equalsIgnoreCase("PLATO")){
                    String sql = "UPDATE platoespecialpedido SET PLAE_CANTIDAD = "+cantidad+" WHERE PLAEP_ID = "+idOrden+"";
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
        System.out.println("Entered the obtener restaurante");
        try{
            this.connect();
            String sql = "SELECT RES_ID, CLI_ID, RES_CODIGO, RES_NOMBRE, RES_FOTO, RES_CARRERA, RES_CALLE FROM restaurante where  RES_ID = ?";
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
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "obtener restaurante", ex);
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
        System.out.println("Entered the obtener cliente: "+id);
        try{
            this.connect();
            String sql = "SELECT CLI_ID, CLI_NOMBRE, CLI_CARRERA, CLI_CALLE, CLI_FOTO, CLI_TIPO FROM CLIENTE WHERE CLI_ID = ?";
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
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "obtener restaurante", ex);
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
        System.out.println("Entered the obtener recurso: "+nombre);
        try{
            this.connect();
            String sql = "SELECT REC_NOMBRE, REC_RECURSO FROM RECURSOS WHERE REC_NOMBRE = ?";
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
            Logger.getLogger(RestauranteRepositorioMysql.class.getName()).log(Level.SEVERE, "obtener recurso", ex);
            response = "FALLO";
        }
        return response;
    }
}