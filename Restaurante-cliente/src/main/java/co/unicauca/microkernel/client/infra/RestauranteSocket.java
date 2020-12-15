package co.unicauca.microkernel.client.infra;

import static co.unicauca.microkernel.common.infra.Utilities.loadProperty;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 *establece las configuaciones para la comunicacion con el cliente, (es usada)
 * no modificar esta clase.
 * @author EdynsonMJ
 */
public class RestauranteSocket {
    /**
     * Socket de la aplicación cliente, el metodo de comunicacion
     */
    private java.net.Socket socket = null;
    /**
     * Permite leer la recibir la respuesta del socket
     */
    private Scanner input;
    /**
     * Permite enviar una solicitud por el socket
     */
    private PrintStream output;
    /**
     * Ip del Server Socket, es obtenida del archivo propertis
     */
    private final String IP_SERVER = loadProperty("server.ip");
    /**
     * Puerto del server socket
     */
    private final int PORT = parseInt(loadProperty("server.port"));

    /**
     * Envia una solicitud desde la aplicación cliente al servidor mediate el
     * socket
     *
     * @param requestJson solicitud en formato json
     * @return respuesta del scoket
     * @throws IOException error de entrada y salida
     */
    public String sendStream(String requestJson) throws IOException {
        var response = "";
        input = new Scanner(socket.getInputStream());
        output = new PrintStream(socket.getOutputStream());
        output.flush();

        // Enviar la solicitud
        output.println(requestJson);

        // Procesa la respuesta
        if (input.hasNextLine()) {
            response = input.nextLine();
        }
        return response;
    }

    /**
     * Cierra los flujos input y output
     */
    public void closeStream() {
        output.close();
        input.close();
    }

    /**
     * Desconectar el socket
     */
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            getLogger(RestauranteSocket.class.getName()).log(SEVERE, null, ex);
        }
    }

    /**
     * Conectar o abrir un socket
     *
     * @throws IOException error de entrada y salida
     */
    public void connect() throws IOException {
        socket = new java.net.Socket(IP_SERVER, PORT);
        getLogger("SocketClient").log(INFO, "Socket establecido");
    }

}
