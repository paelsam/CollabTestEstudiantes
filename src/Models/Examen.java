package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Examen implements Serializable {

    // Por si tenemos una versión actual o antigua del objeto
    private static final long serialVersionUID = 8799656478674716638L;

    private ArrayList<Pregunta> preguntas;
    private float notaFinal;
    private int tiempoDuracion; // Debe ser en segundos
    private List<String> listaIntegrantes;
    private String nombre;
    private String rutaArchivoPreguntas;
    private boolean finExamen;

    public Examen(String nombre, int tiempoDuracion, String rutaArchivo) {
        this.nombre = nombre;
        this.tiempoDuracion = tiempoDuracion;
        this.rutaArchivoPreguntas = rutaArchivo;
        this.preguntas = new ArrayList<>();
        this.finExamen = false;
        // Inmediatamente se cargan las preguntas
        cargarExamen();
    }

    public String leerArchivo() {
        BufferedReader br;
        FileReader fr;
        File archivo;
        String linea;
        String contenido = "";

        archivo = new File(rutaArchivoPreguntas);

        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                contenido += "\n" + linea;
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            return "No se encontró ningun archivo";
        } catch (IOException e) {
            return "No se pudo leer la linea";
        }
        return contenido;
    }

    public ArrayList<ArrayList<String>> obtenerDatos(String textoArchivo) {

        ArrayList<ArrayList<String>> listaPreguntas = new ArrayList<>();
        String[] datosArchivo = textoArchivo.split(",");

        // Cada pregunta tiene 7 elementos.
        int elementosPorSubarray = 7;
        int contador = 0;

        for (int i = 0; i < datosArchivo.length / elementosPorSubarray; i++) {
            ArrayList<String> preguntaString = new ArrayList<>();

            for (int j = 0; j < datosArchivo.length; j++) {
                if (j >= contador && j < (contador + 7))
                    preguntaString.add(datosArchivo[j]);
                continue;
            }
            listaPreguntas.add(preguntaString);
            contador += 7;
        }

        return listaPreguntas;
    }

    public void cargarExamen() {
        for (ArrayList<String> pregunta : obtenerDatos(leerArchivo())) {
            String enunciado = pregunta.get(0);
            String descripcion = pregunta.get(1);
            // Las listas no son seralizables, por lo tanto hay que convertirla a un
            // arraylist
            List<String> listadoOpciones = new ArrayList<String>(pregunta.subList(2, 6));
            String opcionCorrecta = pregunta.get(6);
            Pregunta nuevaPregunta = new Pregunta(enunciado, listadoOpciones, opcionCorrecta, descripcion);
            preguntas.add(nuevaPregunta);
        }
    }

    public boolean estaTerminado() {
        int preguntasResueltas = 0;
        for (Pregunta pregunta : preguntas)
            if (pregunta.getEstado().equals("RESPONDIDA"))
                preguntasResueltas++;
        return (preguntasResueltas == preguntas.size()) ? true : false;
    }

    // Getters y setters para acceder y modificar los atributos

    public void setFinExamen(boolean finExamen) {
        this.finExamen = finExamen;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return this.preguntas;
    }

    public int getTiempoDuracion() {
        return this.tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public List<String> getListaIntegrantes() {
        return this.listaIntegrantes;
    }

    public float getNotaFinal() {
        return this.notaFinal;
    }

    public float calcularNotaFinal() {
        int preguntasCorrectas = 0;
        for (Pregunta pregunta : this.preguntas) {
            if (pregunta.getEsCorrecta())
                preguntasCorrectas++;
            continue;
        }
        // Redondeado a dos decimales
        System.out.println(((preguntasCorrectas * 5) / this.preguntas.size()));
        return (float) ((preguntasCorrectas * 5) / this.preguntas.size());
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getRutaArchivoPreguntas() {
        return this.rutaArchivoPreguntas;
    }

    public void setRutaArchivoPreguntas(String nombreArchivo) {
        this.rutaArchivoPreguntas = nombreArchivo;
    }

    public void agregarIntegrante(String nuevoIntegrante) {
        listaIntegrantes.add(nuevoIntegrante);
    }

    public static String convertirSegundosAMinutos(int segundos) {
        String formato = "";
        double minutos = (double) segundos / 60.0;
        int minutosEnt = segundos / 60;
        double seg = (minutos - minutosEnt) * 60;
        formato += minutosEnt + " minutos, " + (int) seg + " segundos";
        return formato;
    }

    public String toString() {
        String preguntasString = "";
        for (Pregunta pregunta : preguntas) {
            preguntasString += pregunta.toString() + "\n---------------------------\n";
        }

        return "Nombre del examen: " + this.nombre + "\n"
                + "Archivo de preguntas: " + this.rutaArchivoPreguntas + "\n"
                + "Integrantes: " + this.listaIntegrantes + "\n"
                + "Tiempo de duración: " + convertirSegundosAMinutos(tiempoDuracion) + "\n"
                + "Nota final: " + this.notaFinal + "\n"
                + "Preguntas: " + "\n---------------------------\n" + preguntasString;
    }
}