package Controllers;

import java.util.Timer;
import java.util.TimerTask;

import Models.Estudiante;
import Models.Examen;
import Views.GUICliente;

public class ControladorCliente {
    
    private static Estudiante estudiante;
    private static GUICliente guiCliente;
    private static Examen examenRecibido;
    private static Timer temporizador;


    // Variables para el temporizador
    private static int minutosRestantes, segundosRestantes;

    public ControladorCliente() {
        estudiante = new Estudiante("200.0.0.1", 10000);
        guiCliente = new GUICliente();
        temporizador = new Timer();
        // estudiante.ejecutarSocketEstudiante();
        guiCliente.iniciarComponentes();
    }

    public static void mostrarTiempoConsola() {
        String tiempoRestante = "";
        if (minutosRestantes < 10)
            tiempoRestante += "0" + minutosRestantes;
        else
            tiempoRestante += minutosRestantes;

        tiempoRestante += ":";

        if (segundosRestantes < 10)
            tiempoRestante += "0" + segundosRestantes;
        else
            tiempoRestante += segundosRestantes;

        if (segundosRestantes == 0) {
            if (minutosRestantes != 0) {
                minutosRestantes--;
                segundosRestantes = 59;
            }
        } else {
            segundosRestantes--;
        }
        guiCliente.setTiempoRestante(tiempoRestante);
    }

     public static void iniciarCuentaRegresiva(int tiempoTotalSegundos) {
        minutosRestantes = (tiempoTotalSegundos / 60);
        segundosRestantes = tiempoTotalSegundos % 60;

        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if ( !(minutosRestantes == 0 && segundosRestantes == 0) ) {
                    mostrarTiempoConsola();
                } else {
                    // Modificar esto
                    guiCliente.setTiempoRestante("00:00");
                    temporizador.cancel();
                    temporizador.purge();
                    System.out.println("Fin del examen");
                }
            }

        }, 0, 1000);
    }



}
