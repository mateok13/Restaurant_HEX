/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.client.gestionTabla;

import static co.unicauca.microkernel.client.gestionTabla.StructRacionesPed.*;
import co.unicauca.microkernel.common.entities.RacionDia;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jafes
 */
public class TablaRacionesPed {
    public void ver_tabla(JTable tabla, List<RacionDia> raciones) {
        tabla.setDefaultRenderer(Object.class, new Render());

        //lista de titulos
        var titulosList = new ArrayList<String>();

        titulosList.add("ID");
        titulosList.add("NOMBRE");
        titulosList.add("DIA");
        titulosList.add("TIPO");
        titulosList.add("PRECIO");
        titulosList.add(" ");

        //copiar titulos
        var titulos = new String[titulosList.size()];
        for (var i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }

        var data =obtenerMatrizDatos(titulosList,raciones);
        
        DefaultTableModel d = new DefaultTableModel(data, titulos)  {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
        tabla.setModel(d);

        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());

    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, List<RacionDia> raciones) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        var tamaño = titulosList.size();
        Object[][] informacion = null;
        //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
        try {
            informacion=new Object[raciones.size()][tamaño];
            for (var x = 0; x < informacion.length; x++) {
                var btnAgregar = new JButton("Agregar");
                btnAgregar.setName("Agregar");
                //btnAgregar.setSize(new Dimension (100,50));

                informacion[x][ID] = raciones.get(x).getRacId() + "";
                informacion[x][NOMBRE] = raciones.get(x).getNombre() + "";
                informacion[x][TIPO] = raciones.get(x).getTipo() + "";
                informacion[x][PRECIO] = raciones.get(x).getPrecio() + "";
                informacion[x][DIA] = raciones.get(x).getMenuId() + "";
                informacion[x][AGREGAR] = btnAgregar;

            }
        } catch (Exception e) {
        }
        
        return informacion;
    }
}
