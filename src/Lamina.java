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
        botones = new JButton[17];

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

        // Botones de operaciones ajustados a la derecha
        botones[10] = crearBoton("-", 800, 120, 150, 50);
        botones[11] = crearBoton("+", 800, 180, 150, 50);
        botones[12] = crearBoton("*", 800, 240, 150, 50);
        botones[13] = crearBoton("/", 800, 300, 150, 50);
        botones[14] = crearBoton("=", 800, 360, 150, 50);
        botones[15] = crearBoton("Borrar", 800, 480, 150, 50);
        botones[16] = crearBoton(",", 800, 420, 150, 50);

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
        // Iteramos por los botones para identificar cuál se pulsó
        for (JButton boton : botones) {
            if (boton != null && e.getSource() == boton) { // Verifica que el botón no sea nulo
                // Obtenemos el texto del botón pulsado
                String textoBoton = boton.getText();
                // Añadimos ese texto al cuadro de texto
                cuadrado.setText(cuadrado.getText() + textoBoton);
                break;
            }
        }
    }
}    

