package Views;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controllers.ControladorCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUICliente extends JFrame {

    private static Color background = new Color(10, 13, 34);
    private static Color azul = new Color(41, 107, 170);
    private static Color Verde = new Color(3, 166, 107);
    private static Color blanco = new Color(252, 255, 255);
    private static Color Amarillo = new Color(243, 172, 0);
    private static Color Rojo = new Color(200, 25, 34);

    JPanel pNorte, pOpciones, pEnunciado, pPreguntas, pOeste, pResultado;

    JLabel lOpcionesText;
    JLabel lListaPreguntas, lEnunciado, lEstadoPregunta, lResultados;

    JLabel lTiempoRestanteText;
    JLabel lTiempoRestante;

    private JComboBox<String> listaPreguntas;

    JScrollPane scrollPanelResultados;
    JTextArea tADecripcionPregunta, tADescripcionResultados;
    JRadioButton[] rbOpciones;

    private ButtonGroup grupoOpciones;

    JButton bObtener, bCancelar, bResponder, bCerrarVentana;

    public GUICliente() {
        setSize(800, 500);
        setTitle("CollabTest: Estudiantes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void iniciarComponentes() {
        lListaPreguntas = new JLabel("Preguntas: ");
        listaPreguntas = new JComboBox<>();
        listaPreguntas.setPreferredSize(new Dimension(150, 30));
        lListaPreguntas.setForeground(blanco);

        // Label de opciones
        lOpcionesText = new JLabel("Opciones: ");
        lOpcionesText.setForeground(blanco);
        lOpcionesText.setFont(new Font("Arial", Font.BOLD, 14));
        lOpcionesText.setPreferredSize(new Dimension(500, 20));

        grupoOpciones = new ButtonGroup();
        crearJRadioButtons();

        bObtener = new JButton("Obtener");
        bObtener.setBackground(Verde);
        bObtener.setForeground(blanco);
        bCancelar = new JButton("Cancelar");
        bCancelar.setBackground(Rojo);
        bCancelar.setForeground(blanco);

        bResponder = new JButton("Responder");
        bResponder.setPreferredSize(new Dimension(200, 30));
        bResponder.setBackground(Color.ORANGE);

        lTiempoRestanteText = new JLabel("Tiempo restante:");
        lTiempoRestante = new JLabel("00:00");
        lTiempoRestante.setFont(new Font("Arial", Font.BOLD, 24));
        lTiempoRestante.setForeground(blanco);
        lTiempoRestanteText.setForeground(blanco);

        lEnunciado = new JLabel("Enunciado: ");
        tADecripcionPregunta = new JTextArea(20, 40);
        tADecripcionPregunta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lEnunciado.setForeground(Amarillo);

        pOeste = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pEnunciado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pPreguntas = new JPanel(new GridLayout(4, 2));

        pOeste.setBackground(background);
        pEnunciado.setBackground(background);
        pPreguntas.setBackground(background);

        lEstadoPregunta = new JLabel("");
        lEstadoPregunta.setFont(new Font("Arial", Font.BOLD, 24));
        lEstadoPregunta.setBackground(blanco);

        pPreguntas.add(rbOpciones[0]);
        pPreguntas.add(rbOpciones[1]);
        pPreguntas.add(rbOpciones[2]);
        pPreguntas.add(rbOpciones[3]);

        pOeste.setPreferredSize(new Dimension(250, 400));
        pOeste.add(lListaPreguntas);
        pOeste.add(listaPreguntas);
        pOeste.add(bObtener);
        pOeste.add(bCancelar);
        pOeste.add(lTiempoRestanteText);
        pOeste.add(lTiempoRestante);
        pOeste.add(lEstadoPregunta);

        pEnunciado.setPreferredSize(new Dimension(450, this.getHeight()));
        pEnunciado.add(lEnunciado);
        pEnunciado.add(tADecripcionPregunta);
        pEnunciado.add(lOpcionesText);
        pEnunciado.add(pPreguntas);

        add(pOeste, BorderLayout.WEST);
        add(pEnunciado, BorderLayout.CENTER);
        add(bResponder, BorderLayout.SOUTH);

        EventListener evento = new EventListener();
        bObtener.addActionListener(evento);
        bResponder.addActionListener(evento);
        bCancelar.addActionListener(evento);

        listaPreguntas.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (ControladorCliente.verificarEstadoLibre(listaPreguntas.getSelectedIndex())) {
                        habilitarDesabilitarBObtener(true);
                        habilitarDesabilitarBResponder(true);
                    } else if (!ControladorCliente.verificarEstadoLibre(listaPreguntas.getSelectedIndex())) {
                        habilitarDesabilitarBObtener(false);
                        System.out.println(ControladorCliente.verificarEstadoLibre(listaPreguntas.getSelectedIndex()));
                    } else {
                        habilitarDesabilitarBResponder(true);
                    }

                    cambiarLabelEstadoPregunta(listaPreguntas.getSelectedIndex());
                }

            }

        });

        pack();
        setVisible(true);
    }

    public void iniciarComponentesResultados() {

        this.getContentPane().remove(pOeste);
        this.getContentPane().remove(pEnunciado);
        this.getContentPane().remove(bResponder);

        lResultados = new JLabel("Resultados");
        lResultados.setForeground(blanco);
        tADescripcionResultados = new JTextArea(20, 40);
        tADescripcionResultados.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        scrollPanelResultados = new JScrollPane(tADescripcionResultados);

        pResultado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        pResultado.setPreferredSize(new Dimension(450, this.getHeight()));
        pResultado.add(lResultados);
        pResultado.add(scrollPanelResultados);

        bCerrarVentana = new JButton("SALIR DEL EXAMEN");
        bCerrarVentana.setPreferredSize(new Dimension(200, 30));
        bCerrarVentana.setBackground(Color.ORANGE);
        pResultado.add(bCerrarVentana, BorderLayout.SOUTH);

        add(pResultado, BorderLayout.CENTER);

        EventListener evento = new EventListener();
        bCerrarVentana.addActionListener(evento);

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

    public void setTADescripcionPregunta(String descripcion) {
        tADecripcionPregunta.setText(descripcion);
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

    public void habilitarDesabilitarBObtener(boolean bool) {
        bObtener.setEnabled(bool);
        if (bool) {
            cambiarLabelEstadoPregunta(1);
        }
    }

    public JComboBox<String> getListaPreguntas() {
        return listaPreguntas;
    }

    public void habilitarDesabilitarBResponder(boolean bool) {
        bResponder.setEnabled(bool);
    }

    public void cambiarLabelEstadoPregunta(int indice) {
        int estado = ControladorCliente.getPreguntaPorIndice(indice).getEstadoIndex();

        if (estado == 0) {
            lEstadoPregunta.setText("LIBRE");
        } else if (estado == 1) {
            lEstadoPregunta.setText("OCUPADA");
        } else if (estado == 2) {
            lEstadoPregunta.setText("RESPONDIDA");
        }
    }

    public class EventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bObtener) {
                ControladorCliente.getIndicePreguntaActual(listaPreguntas.getSelectedItem().toString());
                ControladorCliente.mostrarPregunta();
                ControladorCliente.cambiarPreguntaAOcupada();
                System.out.println(ControladorCliente.getIndicePreguntaActual());
                listaPreguntas.setEnabled(false);

            }
            if (e.getSource() == bResponder) {
                ControladorCliente.responderPregunta(grupoOpciones.getSelection().getActionCommand());
                habilitarDesabilitarBResponder(false);
                listaPreguntas.setEnabled(true);
            }
            if (e.getSource() == bCancelar) {
                ControladorCliente.liberarPreguntaOcupada();
                habilitarDesabilitarBObtener(true);
                listaPreguntas.setEnabled(true);
            }

            cambiarLabelEstadoPregunta(listaPreguntas.getSelectedIndex());

        }
    }

}
