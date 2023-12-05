
import java.util.ArrayList;

import Controllers.Controlador;
import Models.Examen;
import Views.GUICliente;

public class App {

    public static void main(String[] args) {
        // Crear una instancia de GUICliente
        // GUICliente GUICliente = new GUICliente();
        // GUICliente.iniciarComponentes();
        Controlador cont = new Controlador("200.0.0.1", 10000);
        // Controlador cont = new Controlador();
        // System.out.println(cont.getExamen().toString());
        // ArrayList<String> a = new ArrayList<>();
        // System.out.println(a.size());
        // Examen examen = new Examen("caca", 40, "src\\assets\\preguntas1.txt");
        // System.out.println(examen.toString());
    }
}
