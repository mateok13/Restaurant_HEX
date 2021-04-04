package co.unicauca.microkernel.core.app;

import co.unicauca.microkernel.core.infra.RestauranteServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Conexión del Backend con el Frontend
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Application {

    public static void main(String[] args) {
        //Inicializar el plugin manager con la ruta base de la aplicación.
        try {
            //EL SERVIDOR NO REQUIERE DE INTERFAZ, ESTO PUEDE QUEDAR ASI
            //se crea el socket
            RestauranteServerSocket server = new RestauranteServerSocket();
            //se inicia
            server.start();
        } catch (Exception ex) {
            Logger.getLogger("Application").log(Level.SEVERE, "Error al ejecutar la aplicación", ex);
        }
    }
}
