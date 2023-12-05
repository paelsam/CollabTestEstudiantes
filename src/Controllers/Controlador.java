package Controllers;

import java.io.IOException;

import Models.Estudiante;
import Models.Examen;
import Models.Pregunta;
import Views.GUICliente;

public class Controlador {
    private Examen examen;
    // private Pregunta actual;
    int indicePreguntaActual;

    private GUICliente gui;
    private Estudiante con;

    public Controlador(String HOST, int PORT) {
        // this.examen = new Examen("caca", 40, "src\\assets\\preguntas1.txt");
        this.con = new Estudiante(HOST, PORT, this);
        this.gui = new GUICliente(this);
        con.ejecutarSocketEstudiante();

    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Examen getExamen() {
        return examen;
    }

    public void sacarIndicePreguntaActual(String i) {
        indicePreguntaActual = Integer.parseInt(i);
    }

    public void GuardarRespuesta(String respuesta) {
        if (examen.getPreguntas().get(indicePreguntaActual).verificarOpcion(respuesta)) {
            examen.getPreguntas().get(indicePreguntaActual).setEsCorrecta(true);

        }
        examen.getPreguntas().get(indicePreguntaActual).setEstado(2);
        System.out.println(
                "---------------------------------\n" + examen.getPreguntas().get(indicePreguntaActual).getEstado()
                        + "\n------------------------------------");
        try {
            con.enviarExamen(examen);
        } catch (IOException e) {
            System.out.println("error al enviar el examen desde GuardarRespuesta");
        }
        examen.getPreguntas().get(indicePreguntaActual).setEsCorrecta(false);
    }

    public void cambiarEstadoDePregunta(int estado) {
        examen.getPreguntas().get(indicePreguntaActual).setEstado(estado);

    }

    public Pregunta getPreguntaActual() {
        return examen.getPreguntas().get(indicePreguntaActual);
    }

    public int getIndicePreguntaActual() {
        return indicePreguntaActual;
    }

    public void setIndicePreguntaActual(int indicePreguntaActual) {
        this.indicePreguntaActual = indicePreguntaActual;
    }

    public GUICliente getGui() {
        return this.gui;
    }

    public void setGui(GUICliente gui) {
        this.gui = gui;
    }

    public Estudiante getCon() {
        return con;
    }

    public void setCon(Estudiante con) {
        this.con = con;
    }

}
