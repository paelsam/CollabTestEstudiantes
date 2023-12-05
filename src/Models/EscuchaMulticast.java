package Models;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Controllers.Controlador;

public class EscuchaMulticast extends Thread {
    MulticastSocket multicastSocket;
    InetAddress inetAddress;
    Controlador cont;

    public EscuchaMulticast(Controlador cont) {
        this.cont = cont;
        try {
            System.out.println("Iniciando escucha del multicast....");
            this.multicastSocket = new MulticastSocket(9999);
            inetAddress = InetAddress.getByName("224.0.0.2");
            multicastSocket.joinGroup(inetAddress);
            start();
        } catch (IOException e) {
            System.out.println("Error multicast: " + e);
        }
    }

    @Override
    public void run() {
        byte[] examenRecibido = new byte[6400];
        DatagramPacket datagram = new DatagramPacket(examenRecibido, examenRecibido.length);
        Examen salida;
        while (true) {
            try {
                System.out.println("Recibiendo datagrama...");
                multicastSocket.receive(datagram);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(examenRecibido);
                ObjectInputStream oInputStream = new ObjectInputStream(new BufferedInputStream(byteArrayInputStream));
                salida = (Examen) oInputStream.readObject();
                oInputStream.close();
                cont.setExamen(salida);
                cont.getGui().setItems(salida.getPreguntas());

                System.out.println(salida.getPreguntas());

            } catch (IOException e) {
                System.out.println("Error al recibir datos: " + e);
                cerrarMulti();
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Error al recibir datos: " + e);
                break;
            }
        }
    }

    public void cerrarMulti() {
        try {
            multicastSocket.leaveGroup(inetAddress);
            multicastSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}