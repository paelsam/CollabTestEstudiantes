package Controllers;

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

    public Controlador() {
        this.examen = new Examen("caca", 40, "src\\assets\\preguntas1.txt");
    }

    public Controlador(String HOST, int PORT) {
        this.examen = new Examen("caca", 40, "src\\assets\\preguntas1.txt");

        this.gui = new GUICliente(this);
        this.con = new Estudiante(HOST, PORT, this);

    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Examen getExamen() {
        return examen;
    }

    public void sacarPreguntaActual(String i) {
        indicePreguntaActual = Integer.parseInt(i);
    }

    public void GuardarRespuesta(String respuesta) {
        if (examen.getPreguntas().get(indicePreguntaActual).getOpcionCorrecta().equals(respuesta)) {

            examen.getPreguntas().get(indicePreguntaActual).setEstado(2);

        }

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
