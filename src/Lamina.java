import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Lamina extends JPanel implements ActionListener {
    private JTextField cuadrado; // CUADRO DE TEXTO
    private JButton[] botones;  // ARRAY PARA LOS 10 NÚMEROS

    public Lamina() {
        setLayout(null); // Configuramos el layout manualmente

        // CUADRO DE TEXTO
        cuadrado = new JTextField();
        cuadrado.setBounds(5, 5, 950, 100); // Dimensiones
        cuadrado.setEditable(false); // Deshabilitamos la edición manual
        add(cuadrado); // Añadimos el cuadro de texto

        // INICIALIZAMOS EL ARRAY DE LOS BOTONES
        botones = new JButton[10];

        // CONFIGURAMOS LOS BOTONES MANUALMENTE
        botones[0] = crearBoton("0", 10, 300, 100, 50);
        botones[1] = crearBoton("1", 10, 240, 100, 50);
        botones[2] = crearBoton("2", 120, 240, 100, 50);
        botones[3] = crearBoton("3", 230, 240, 100, 50);
        botones[4] = crearBoton("4", 10, 180, 100, 50);
        botones[5] = crearBoton("5", 120, 180, 100, 50);
        botones[6] = crearBoton("6", 230, 180, 100, 50);
        botones[7] = crearBoton("7", 10, 120, 100, 50);
        botones[8] = crearBoton("8", 120, 120, 100, 50);
        botones[9] = crearBoton("9", 230, 120, 100, 50);

        // REGISTRAMOS LOS EVENTOS DE LOS BOTONES
        for (JButton boton : botones) {
            boton.addActionListener(this);
            add(boton);
        }
    }

    /**
     * Método auxiliar para crear botones con propiedades personalizadas.
     */
    private JButton crearBoton(String texto, int x, int y, int width, int height) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, width, height); // Configuramos posición y tamaño
        boton.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente grande
        return boton;
    }

    /**
     * Sobrescribimos paintComponent para personalizar el fondo o el diseño si es necesario.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpia el panel antes de dibujar
    }

    /**
     * Manejo de eventos: agrega el número del botón pulsado al cuadro de texto.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // VERIFICA QUE BOTON SE PULSA
        for (int i = 0; i < botones.length; i++) {
            if (e.getSource() == botones[i]) {
                cuadrado.setText(cuadrado.getText() + i);   // AÑADE EL NÚMERO AL CUADRO
                break;
            }
        }
    }
}
