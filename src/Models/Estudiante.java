package Models;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Estudiante {

    private String nombreEstudiante; 
    
    EscuchaMulticast escuchaMulticast;

    Socket estudiante;
    ObjectOutputStream salida;
    ObjectInputStream entrada;

    String HOST;
    int PORT;

    public Estudiante(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public void ejecutarSocketEstudiante() {
        try {
            conectarAlServidor();
            obtenerFlujos();
            while (!estudiante.isClosed()) {} // Bucle infinito para que no se cierre
        } catch (IOException e) {
            if (estudiante.isClosed()) {
                System.out.println("El no hay conexión con el serivdor");
                cerrarConexion();
            }
        } finally {
            cerrarConexion();
        }
    }

    public void conectarAlServidor() throws IOException {
        System.out.println("Intentando establecer conexión....");
        estudiante = new Socket("0.0.0.0", PORT);
        System.out.println("Conectado en: " + estudiante.getInetAddress());
        escuchaMulticast = new EscuchaMulticast();
    }

    public void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(estudiante.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(estudiante.getInputStream());
        System.out.println("Se obtuvieron los flujos E/S");
    }

    public void cerrarConexion() {
        System.out.println("Cerrando conexion....");
        try {
            estudiante.close();
            salida.close();
            entrada.close();
        } catch (IOException ex) {
            System.out.println("Error cerrando: " + ex);
        }
    }

    public void enviarExamen(Examen examen) throws IOException {
        try {
            salida.writeObject(examen);
            salida.flush();
        } catch (EOFException e) {
            System.out.println("Error al mandar datos al servidor: " + e);
        }
    }
}