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
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GUICliente extends JFrame {

    private static Color background = new Color(10, 13, 34);
    private static Color azul = new Color(41, 107, 170);
    private static Color Verde = new Color(3, 166, 107);
    private static Color blanco = new Color(252, 255, 255);
    private static Color Amarillo = new Color(243, 172, 0);
    private static Color Rojo = new Color(200, 25, 34);

    JPanel pNorte, pOpciones, pEnunciado, pPreguntas, pOeste, pResultado;

    JLabel lOpcionA, lOpcionB, lOpcionC, lOpcionD;
    JLabel lListaPreguntas, lEnunciado, lResultados;

    public JComboBox<String> getListaPreguntas() {
        return listaPreguntas;
    }

    JLabel lTiempoRestanteText;
    JLabel lTiempoRestante;

    private JComboBox<String> listaPreguntas;
    JScrollPane scrollPanelResultados;
    JTextArea tADecripcionPregunta, tADecripcionResultados;
    JRadioButton[] rbOpciones;

    private ButtonGroup grupoOpciones;

    JButton bObtener, bCancelar, bResponder, bCerrarVentana;

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
        lListaPreguntas.setForeground(blanco);
        addItems(new String[] { "1", "2", "3" });

        // Label de opciones
        lOpcionA = new JLabel("a.");
        lOpcionB = new JLabel("b.");
        lOpcionC = new JLabel("c.");
        lOpcionD = new JLabel("d.");

        lOpcionA.setForeground(blanco);
        lOpcionB.setForeground(blanco);
        lOpcionC.setForeground(blanco);
        lOpcionD.setForeground(blanco);

        grupoOpciones = new ButtonGroup();
        crearJRadioButtons();
        setRadioButtons(new String[] { "Ajá", "Ejé", "Ilo", "Ola" });
    
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
        tADecripcionPregunta.setBorder(BorderFactory.createLineBorder(blanco));
        lEnunciado.setForeground(Amarillo);

        pOeste = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pEnunciado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pPreguntas = new JPanel(new GridLayout(4, 2));

        pOeste.setBackground(background);
        pEnunciado.setBackground(background);
        pPreguntas.setBackground(background);

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
        bCancelar.addActionListener(evento);

        listaPreguntas.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // int indicePreguntaActual = ControladorCliente.getIndicePreguntaActual();

                if (e.getStateChange() == e.SELECTED) {
                    if (ControladorCliente.verificarEstadoLibre(listaPreguntas.getSelectedIndex())) {
                        habilitarDesabilitarBObtener(true);
                        habilitarDesabilitarBResponder(true);
                    } else if (!ControladorCliente.verificarEstadoLibre(listaPreguntas.getSelectedIndex())) {
                        habilitarDesabilitarBObtener(false);

                    } else {
                        habilitarDesabilitarBResponder(true);
                    }
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
        tADecripcionResultados = new JTextArea(20, 40);
        tADecripcionResultados.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        scrollPanelResultados = new JScrollPane(tADecripcionResultados);

        pResultado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        pResultado.setPreferredSize(new Dimension(450, this.getHeight()));
        pResultado.add(lResultados);
        pResultado.add(scrollPanelResultados);

        bCerrarVentana = new JButton("SALIR DEL EXAMEN");
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

    public void toggleBResponder() {
        bResponder.setEnabled(false);
    }

    public void setTADecripcionPregunta(String descripcion) {
        tADecripcionPregunta.setText(descripcion);
    }

    public void setTADecripcionResultados(String descripcion) {
        tADecripcionResultados.setText(descripcion);
    }

    public void setLEnunciado(String enunciado) {
        lEnunciado.setText("Enunciado: " + enunciado);
    }

    public void habilitarDesabilitarBObtener(boolean bool) {
        bObtener.setEnabled(bool);
    }

    public void habilitarDesabilitarBResponder(boolean bool) {
        bResponder.setEnabled(bool);
    }

    public class EventListener implements ActionListener, KeyListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bObtener) {
                ControladorCliente.getIndicePreguntaActual(listaPreguntas.getSelectedItem().toString());
                ControladorCliente.mostrarPregunta();
                ControladorCliente.cambiarPreguntaAOcupada();
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

        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_R) {
                ControladorCliente.responderPregunta(grupoOpciones.getSelection().getActionCommand());
                habilitarDesabilitarBResponder(false);
                listaPreguntas.setEnabled(true);
            }

            if (e.getKeyChar() == 'o' || e.getKeyChar() == 'O') {
                ControladorCliente.getIndicePreguntaActual(listaPreguntas.getSelectedItem().toString());
                ControladorCliente.mostrarPregunta();
                ControladorCliente.cambiarPreguntaAOcupada();
            }

            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                ControladorCliente.liberarPreguntaOcupada();
                habilitarDesabilitarBObtener(true);
                listaPreguntas.setEnabled(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
