package co.unicauca.microkernel.client.gestionTabla;

import static co.unicauca.microkernel.client.gestionTabla.StructPedidos.*;
import co.unicauca.microkernel.common.entities.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;

/**
 * Tabla de pedidos
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class TablaPedidos {
    public void ver_tabla(JTable tabla, List<Pedido> pedidos) {
        tabla.setDefaultRenderer(Object.class, new Render());
        //lista de titulos
        var titulosList = new ArrayList<String>();
        titulosList.add("PEDIDO");
        titulosList.add("CLIENTE");
        titulosList.add("RESTAURANTE");
        titulosList.add("ESTADO");
        titulosList.add("FECHA");
        //copiar titulos
        var titulos = new String[titulosList.size()];
        for (var i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }
        var data =obtenerMatrizDatos(titulosList,pedidos);
        DefaultTableModel d = new DefaultTableModel(data, titulos){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
        tabla.setModel(d);
        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());
    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, List<Pedido> pedidos) {
        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        var tamaño = titulosList.size();
        Object[][] informacion = null;
        try {
            informacion = new Object[pedidos.size()][tamaño];
            //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
            for (var x = 0; x < informacion.length; x++) {
                informacion[x][ID] = pedidos.get(x).getIdPedido() + "";
                informacion[x][CLIENTE] = pedidos.get(x).getCliente() + "";
                informacion[x][RESTAURANTE] = pedidos.get(x).getResId() + "";
                informacion[x][ESTADO] = pedidos.get(x).getEstado() + "";
                informacion[x][FECHA] = pedidos.get(x).getFechaCreado();
            }
        } catch (Exception e) {
            System.out.println("Error al crear la tabla: "+e.getMessage());
        }
        return informacion;
    }
}
