package Views;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import Controllers.ControladorCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUICliente extends JFrame {

    JPanel pNorte, pOpciones, pEnunciado, pPreguntas, pOeste;

    JLabel lOpcionA, lOpcionB, lOpcionC, lOpcionD;
    JLabel lListaPreguntas, lEnunciado;

    JLabel lTiempoRestanteText;
    JLabel lTiempoRestante;

    JComboBox<String> listaPreguntas;

    JTextArea tADecripcionPregunta;
    JRadioButton[] rbOpciones;
    ButtonGroup grupoOpciones;

    JButton bObtener, bCancelar, bResponder;

    public GUICliente() {
        setSize(700, 500);
        setTitle("CollabTest: Estudiantes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void iniciarComponentes() {
        lListaPreguntas = new JLabel("Preguntas: ");
        listaPreguntas = new JComboBox<>();
        listaPreguntas.setPreferredSize(new Dimension(150, 30));
        addItems(new String[] { "1", "2", "3" });

        // Label de opciones
        lOpcionA = new JLabel("a.");
        lOpcionB = new JLabel("b.");
        lOpcionC = new JLabel("c.");
        lOpcionD = new JLabel("d.");

        grupoOpciones = new ButtonGroup();
        crearJRadioButtons();
        setRadioButtons(new String[] { "Ajá", "Ejé", "Ilo", "Ola" });

        bObtener = new JButton("Obtener");
        bObtener.setBackground(Color.GREEN);
        bCancelar = new JButton("Cancelar");
        bCancelar.setBackground(Color.RED);

        bResponder = new JButton("Responder");
        bResponder.setPreferredSize(new Dimension(200, 30));
        bResponder.setBackground(Color.ORANGE);

        lTiempoRestanteText = new JLabel("Tiempo restante:");
        lTiempoRestante = new JLabel("00:00");
        lTiempoRestante.setFont(new Font("Arial", Font.BOLD, 24));

        lEnunciado = new JLabel("Enunciado: ");
        tADecripcionPregunta = new JTextArea(20, 40);
        tADecripcionPregunta.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pOeste = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pEnunciado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pPreguntas = new JPanel(new GridLayout(4, 2));

        pPreguntas.add(lOpcionA);
        pPreguntas.add(rbOpciones[0]);
        pPreguntas.add(lOpcionB);
        pPreguntas.add(rbOpciones[1]);
        pPreguntas.add(lOpcionC);
        pPreguntas.add(rbOpciones[2]);
        pPreguntas.add(lOpcionD);
        pPreguntas.add(rbOpciones[3]);

        pOeste.setPreferredSize(new Dimension(250, 400));
        pOeste.add(lListaPreguntas);
        pOeste.add(listaPreguntas);
        pOeste.add(bObtener);
        pOeste.add(bCancelar);
        pOeste.add(lTiempoRestanteText);
        pOeste.add(lTiempoRestante);

        pEnunciado.setPreferredSize(new Dimension(450, this.getHeight()));
        pEnunciado.add(lEnunciado);
        pEnunciado.add(tADecripcionPregunta);
        pEnunciado.add(pPreguntas);

        add(pOeste, BorderLayout.WEST);
        add(pEnunciado, BorderLayout.CENTER);
        add(bResponder, BorderLayout.SOUTH);

        EventListener evento = new EventListener();
        bObtener.addActionListener(evento);
        bResponder.addActionListener(evento);

        pack();
        setVisible(true);
    }

    public void crearJRadioButtons() {
        rbOpciones = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            rbOpciones[i] = new JRadioButton();
            grupoOpciones.add(rbOpciones[i]);
        }
    }

    public void setRadioButtons(String[] opciones) {
        for (int i = 0; i < opciones.length; i++) {
            rbOpciones[i].setText(opciones[i]);
            rbOpciones[i].setActionCommand(opciones[i]);
        }
    }

    public void addItems(String[] preguntas) {
        for (String numPregunta : preguntas) {
            listaPreguntas.addItem(numPregunta);
        }
    }

    public void setTiempoRestante(String tiempo) {
        lTiempoRestante.setText(tiempo);
    }

    public void setItems(int numPregunta) {
        listaPreguntas.removeAllItems();
        for (int i = 0; i < numPregunta; i++) {
            listaPreguntas.addItem(String.valueOf(i + 1));
        }
    }

    public void toggleBResponder() {
        bResponder.setEnabled(false);
    }

    public void setTADecripcionPregunta(String descripcion) {
        tADecripcionPregunta.setText(descripcion);
    }

    public void setLEnunciado(String enunciado) {
        lEnunciado.setText("Enunciado: " + enunciado);
    }

    public class EventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bObtener) {
                ControladorCliente.getIndicePreguntaActual(listaPreguntas.getSelectedItem().toString());
                ControladorCliente.mostrarPregunta();
            }
            if (e.getSource() == bResponder) {
                ControladorCliente.responderPregunta(grupoOpciones.getSelection().getActionCommand());
            }
        }
    }

}
