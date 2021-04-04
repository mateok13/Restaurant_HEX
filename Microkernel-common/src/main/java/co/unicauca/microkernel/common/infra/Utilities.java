package co.unicauca.microkernel.common.infra;

import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Utilidades varias utilizadas por otras clases
 *
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Utilities {
    /**
     * Cargar una propiedad de config.properties
     *
     * @param key llave de la propiedad
     * @return valor de la propiedad
     */
    public static String loadProperty(String key) {
        Properties prop = new Properties();
        InputStream is;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo");
        }

        return prop.getProperty(key);
    }
    /**
     * Verifica si un String contiene sólo digitos
     * @param str Cadena a verificvar
     * @return true si contiene sólo digitos, false en caso contrario
     */
    public static boolean isNumeric(String str) {

        boolean resultado;

        try {
            Integer.parseInt(str);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    /**
     * a partir de una ruta carga una imagen a un arrego de bytes
     * @param ruta hubicacion de la foto o imagen
     * @return arreglo de bytes que representan la foto
     */
    public static byte [] convertirFoto(String ruta){
        byte[] icono;
        try {
            File rut=new File(ruta);
            icono = new byte[(int)rut.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
        } catch (Exception ex) {
            return null;
        }
        return icono;
    }
    /**
     * crea un objeto de tipo icon a partir de un arreglo de bytes
     * @param imagen conjunto de bytes
     * @param anchura ancho de la imagen
     * @param altura alto de la imagen
     * @return objeto de iipo icon que representa laimagen
     */
     public static Icon crearIcono(byte[] imagen,int anchura,int altura) {
        Icon iconoEscalado;
        ImageIcon i = new ImageIcon(imagen);
        Image imgEscalada = i.getImage().getScaledInstance(anchura, altura, SCALE_SMOOTH);
        iconoEscalado = new ImageIcon(imgEscalada);
        return iconoEscalado;
    }
    /**
     * obtine el dia actual del sistema
     * @return string que representa el dia
     */
    public static String DiaActual(){
        java.util.Date fecha = new Date();
        int dia = fecha.getDay();
        String myDia=null;
        switch (dia){
            case 1:
                myDia="LUNES";
                break;
            case 2:
                myDia="MARTES";
                break;
            case 3:
                myDia="MIERCOLES";
                break;
            case 4:
                myDia="JUEVES";
                break;
            case 5:
                myDia="VIERNES";
                break;
            case 6:
                myDia="SABADO";
                break;
            case 7:
                myDia="DOMINGO";
                break; 
        }
        return myDia;
    }
    /**
     * encripta las palabras
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes());
    }
     /**
     * desencripta palabras encriptadas previamente
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }

}
