package Models;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Controllers.ControladorCliente;

public class EscuchaMulticast extends Thread {
    MulticastSocket multicastSocket;
    InetAddress inetAddress;

    public EscuchaMulticast() {
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
                if (ControladorCliente.getIniciarTimer() == false) {
                    ControladorCliente.iniciarCuentaRegresiva(salida.getTiempoDuracion());
                    ControladorCliente.setInciarTimer(true);
                }
                ControladorCliente.setExamen(salida);
                ControladorCliente.getGuiCliente().setLNombreExamen(salida.getNombre());
                ControladorCliente.getGuiCliente().setItems(salida.getPreguntas().size());
                ControladorCliente.getGuiCliente().getListaPreguntas()
                        .setSelectedIndex(ControladorCliente.getIndicePreguntaActual());
            } catch (IOException e) {
                System.out.println("Error al recibir datos: " + e);
                cerrarMulticast();
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Error al recibir datos: " + e);
                break;
            }
        }
    }

    public void cerrarMulticast() {
        try {
            multicastSocket.leaveGroup(inetAddress);
            multicastSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}