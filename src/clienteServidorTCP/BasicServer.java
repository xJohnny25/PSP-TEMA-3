package clienteServidorTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class BasicServer {

    public static final int PORT = 4444;

    public static void main(String[] args) throws IOException {
        // Establece el puerto en el que escucha peticiones
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }

        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

        System.out.println("Escuchando: " + socketServidor);
        try {
            // Se bloquea hasta que recibe alguna petición de un cliente
            // abriendo un socket para el cliente

            while (true) {
                socketCliente = socketServidor.accept();
                System.out.println("Conexión aceptada: " + socketCliente);

                new Worker(socketCliente).start();
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        socketServidor.close();
    }

}
