package co.unicauca.microkernel.client.gestionTabla;

import static co.unicauca.microkernel.client.gestionTabla.StructEspecialesHaP.*;
import co.unicauca.microkernel.common.entities.PlatoEspecial;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import javax.swing.table.DefaultTableModel;

/**
 * Tabla de historial de pedidos de platos especiales
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class TablaEspecialesHaP {
public void ver_tabla(JTable tabla, List<PlatoEspecial> especiales) {
        tabla.setDefaultRenderer(Object.class, new Render());
        //lista de titulos
        var titulosList = new ArrayList<String>();
        titulosList.add("ID");
        titulosList.add("NOMBRE");
        titulosList.add("DESCRIPCION");
        titulosList.add("PRECIO");
        titulosList.add("");
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
        //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
        try {
            informacion=new Object[especiales.size()][tamaño];
            for (var x = 0; x < informacion.length; x++) {
                var btnCarritoE = new JButton("CarritoE");
                btnCarritoE.setName("CarritoE");
                informacion[x][ID] = especiales.get(x).getId_pe() + "";
                informacion[x][NOMBRE] = especiales.get(x).getNombre() + "";
                informacion[x][DESCRIPCION] = especiales.get(x).getDescripcion() + "";
                informacion[x][PRECIO] = especiales.get(x).getPrecio() + "";
                informacion[x][CARRITOE] = btnCarritoE;
            }
        } catch (Exception e) {
            System.out.println("Error al crear la tabla: "+e.getMessage());
        }
        return informacion;
    }
}

