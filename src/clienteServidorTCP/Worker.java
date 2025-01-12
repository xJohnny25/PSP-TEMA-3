package clienteServidorTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker extends Thread {
    private Socket socketCliente;
    private BufferedReader entrada = null;
    private PrintWriter salida = null;

    public Worker(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())));

            String mensajeRecibido, mensajeEnviado;

            while (true) {
                // Hacemos una recepción de información desde el cliente
                mensajeRecibido = entrada.readLine();

                if (mensajeRecibido == null) {
                    System.out.println("Cerrando conexión...");
                    break;
                }

                System.out.println(Thread.currentThread().getId() + " -> " + mensajeRecibido);

                // Hacemos un envío al cliente
                mensajeEnviado = "Mensaje enviado desde el servidor al cliente";
                salida.println(mensajeEnviado);
                System.out.println("--> Cliente: " + mensajeEnviado);

                if (mensajeRecibido.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo...");
                    break;
                }
            }

            salida.close();
            entrada.close();
            socketCliente.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
