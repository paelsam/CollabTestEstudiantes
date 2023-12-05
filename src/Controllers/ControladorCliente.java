package Controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Models.Estudiante;
import Models.Examen;
import Models.Pregunta;
import Views.GUICliente;

public class ControladorCliente {
    
    private static Estudiante estudiante;
    private static GUICliente guiCliente;
    private static Examen examen;
    private static Timer temporizador;
    private static int indicePreguntaActual;


    // Variables para el temporizador
    private static int minutosRestantes, segundosRestantes;

    public ControladorCliente() {
        // Pruebas
        // examen = new Examen("caca", 40, "src\\assets\\preguntas1.txt");
        // Fin Pruebas 

        estudiante = new Estudiante("200.0.0.1", 10000);
        guiCliente = new GUICliente();
        temporizador = new Timer();
        guiCliente.iniciarComponentes();
        estudiante.ejecutarSocketEstudiante();
        // iniciarCuentaRegresiva(examen.getTiempoDuracion());
    }

    public static void tiempoRestanteTexto() {
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
                    tiempoRestanteTexto();
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


    public Examen getExamen() {
        return ControladorCliente.examen;
    }

    public static void setExamen(Examen examen) {
        ControladorCliente.examen = examen;
    }

    public static void getIndicePreguntaActual(String index) {
        indicePreguntaActual = Integer.parseInt(index) - 1;
    }

    public static void mostrarPregunta() {
        guiCliente.setLEnunciado(getPreguntaActual().getEnunciado());
        guiCliente.setTADecripcionPregunta(getPreguntaActual().getDescripcion());
        guiCliente.setItems(examen.getPreguntas().size()); // Numero de preguntas
        guiCliente.setRadioButtons(getPreguntaActual().getListaOpciones().toArray(new String[getPreguntaActual().getListaOpciones().size()-1]));
    }

    public static void responderPregunta(String respuesta) {
        if ( examen.getPreguntas().get(indicePreguntaActual).verificarOpcion(respuesta) )
            examen.getPreguntas().get(indicePreguntaActual).setEsCorrecta(true);
        cambiarEstadoPregunta(2);
        try {
            estudiante.enviarExamen(examen);
        } catch (IOException e) {
            System.out.println("Error al enviar examen: " + e);
        }
    }

    public static void cambiarEstadoPregunta(int estado) {
        examen.getPreguntas().get(indicePreguntaActual).setEstado(estado);
    }

    public static Pregunta getPreguntaActual() {
        return examen.getPreguntas().get(indicePreguntaActual);
    }

    public static GUICliente getGuiCliente() {
        return ControladorCliente.guiCliente;
    }

  

}
