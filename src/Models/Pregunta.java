package Models;

import java.io.Serializable;
import java.util.List;

public class Pregunta implements Serializable {

    private static final long serialVersionUID = 8799656478674716639L;


    private final String[] estados = {"LIBRE", "OCUPADA", "RESPONDIDA"};

    private final String enunciado;
    private List<String> listaOpciones;
    private final String opcionCorrecta;
    private final String descripcion;
    private int estado;
    private String respondidoPor;
    private boolean esCorrecta;

    public Pregunta(String enunciado, List<String> listadoOpciones, String opcionCorrecta, String descripcion) {
        this.enunciado = enunciado;
        this.listaOpciones = listadoOpciones;
        this.opcionCorrecta = opcionCorrecta;
        this.descripcion = descripcion;
        this.estado = 0;
        this.respondidoPor = "";
        this.esCorrecta = false;
    }

    public String getEnunciado() {
        return this.enunciado;
    }

    public List<String> getListaOpciones() {
        return this.listaOpciones;
    }

    public String getOpcionCorrecta() {
        return this.opcionCorrecta;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getEstadoIndex() {
        return this.estado;
    }

    public String getEstado() {
        return this.estados[this.estado];
    }

    public String[] getEstados() {
        return this.estados;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getRespondidoPor() {
        return this.respondidoPor;
    }

    public void setRespondidoPor(String respondidoPor) {
        this.respondidoPor = respondidoPor;
    }

    public boolean getEsCorrecta() {
        return this.esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public boolean verificarOpcion(String opcion) {
        if ( opcionCorrecta.trim().equals(opcion) )
            return true;
        return false;
    }

    public String toString() {
        return "Enunciado: " + this.enunciado.trim() + "\n" 
        + "Descripción: " + this.descripcion + "\n"
        + "Opciones: " + this.listaOpciones + "\n"
        + "Opción correcta: " + this.opcionCorrecta + "\n";

    }

}