/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.client.gestionTabla;

import co.unicauca.microkernel.common.entities.PlatoEspecial;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.DESCRIPCION;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.ELIMINAR;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.ID;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.MODIFICAR;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.NOMBRE;
import static co.unicauca.microkernel.client.gestionTabla.StructEspeciales.PRECIO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EdynsonMJ
 */
public class TablaEspeciales {
    public void ver_tabla(JTable tabla, List<PlatoEspecial> especiales) {
        tabla.setDefaultRenderer(Object.class, new Render());

        //lista de titulos
        var titulosList = new ArrayList<String>();

        titulosList.add("ID");
        titulosList.add("NOMBRE");
        titulosList.add("DESCRIPCION");
        titulosList.add("PRECIO");
        titulosList.add("");
        titulosList.add(" ");

        //copiar titulos
        var titulos = new String[titulosList.size()];
        for (var i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }

        var data =obtenerMatrizDatos(titulosList,especiales);
        
        DefaultTableModel d = new DefaultTableModel(data, titulos){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.setAutoResizeMode(AUTO_RESIZE_OFF);
        tabla.setModel(d);

        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());

    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, List<PlatoEspecial> especiales) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        var tamaño = titulosList.size();
        Object[][] informacion = null;
        try {
            informacion=new Object[especiales.size()][tamaño];
        //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
        for (var x = 0; x < informacion.length; x++) {
            var btnModificar = new JButton("modificar");
            btnModificar.setName("modificar");
            var btnEliminar = new JButton("eliminar");
            btnEliminar.setName("eliminar");
            
            informacion[x][ID] = especiales.get(x).getId_pe() + "";
            informacion[x][NOMBRE] = especiales.get(x).getNombre() + "";
            informacion[x][DESCRIPCION] = especiales.get(x).getDescripcion() + "";
            informacion[x][PRECIO] = especiales.get(x).getPrecio() + "";
            informacion[x][ELIMINAR] = btnEliminar;
            informacion[x][MODIFICAR] = btnModificar;
        }
        } catch (Exception e) {
        }
        return informacion;
    }
    
}
