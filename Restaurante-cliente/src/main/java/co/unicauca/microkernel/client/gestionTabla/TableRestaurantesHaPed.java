/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.client.gestionTabla;

import static co.unicauca.microkernel.client.gestionTabla.StructRestaurantesHaPed.*;
import co.unicauca.microkernel.common.entities.Restaurante;
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
public class TableRestaurantesHaPed {
    public void ver_tabla(JTable tabla, List<Restaurante> restaurantes) {
        tabla.setDefaultRenderer(Object.class, new Render());

        //lista de titulos
        var titulosList = new ArrayList<String>();

        titulosList.add("ID");
        titulosList.add("NOMBRE");
        titulosList.add("CALLE");
        titulosList.add("CARRERA");
        titulosList.add(" ");

        //copiar titulos
        var titulos = new String[titulosList.size()];
        for (var i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }
        
        var data =obtenerMatrizDatos(titulosList,restaurantes);
        
        DefaultTableModel d = new DefaultTableModel(data, titulos)  {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
        tabla.setModel(d);

        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());

    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, List<Restaurante> restaurantes) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        var tamaño = titulosList.size();
        Object[][] informacion = null;
        //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
        try {
            informacion=new Object[restaurantes.size()][tamaño];
            for (var x = 0; x < informacion.length; x++) {
                var btnPedir = new JButton("Pedir");
                btnPedir.setName("Pedir");
                //btnAgregar.setSize(new Dimension (100,50));

                informacion[x][ID] = restaurantes.get(x).getId() + "";
                informacion[x][NOMBRE] = restaurantes.get(x).getNombre() + "";
                informacion[x][CALLE] = restaurantes.get(x).getCalle() + "";
                informacion[x][CARRERA] = restaurantes.get(x).getCarrera() + "";
                informacion[x][PEDIR] = btnPedir;

            }
            
        } catch (Exception e) {
            
        }
        return informacion;
    }
}
