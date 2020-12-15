/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.client.gestionTabla;

import static co.unicauca.microkernel.client.gestionTabla.StructCarritoRacionCf.*;
import co.unicauca.microkernel.common.entities.CarritoG;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jafes
 */
public class TablaCarritoRacionCf {

    public void ver_tabla(JTable tabla, List<CarritoG> carritoR) {
        tabla.setDefaultRenderer(Object.class, new Render());

        //lista de titulos
        var titulosList = new ArrayList<String>();

        titulosList.add("ID");
        titulosList.add("NOMBRE");
        titulosList.add("PRECIO");
        titulosList.add("CANTIDAD");

        //copiar titulos
        var titulos = new String[titulosList.size()];
        for (var i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }

        var data =obtenerMatrizDatos(titulosList,carritoR);
        
        DefaultTableModel d = new DefaultTableModel(data, titulos)  {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
        tabla.setModel(d);

        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());

    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, List<CarritoG> carritoR) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        var tamaño = titulosList.size();
        Object[][] informacion = null;
        //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
        try {
            informacion=new Object[carritoR.size()][tamaño];
            for (var x = 0; x < informacion.length; x++) {

                informacion[x][ID] = carritoR.get(x).getIdCarrito() + "";
                informacion[x][NOMBRE] = carritoR.get(x).getNombre() + "";
                informacion[x][PRECIO] = carritoR.get(x).getPrecio() + "";
                informacion[x][CANTIDAD] = carritoR.get(x).getCantidad() + "";

            }
            
        } catch (Exception e) {
        }
        
        return informacion;
    }
    

}

