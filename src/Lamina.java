import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Lamina extends JPanel implements ActionListener {
    private JTextField cuadrado; // CUADRO DE TEXTO
    private JButton[] botones;  // ARRAY PARA LOS BOTONES
    private String operacionActual = ""; // Operación en curso
    private double primerNumero = 0;    // Primer número ingresado

    public Lamina() {
        setLayout(null); // Configuramos el layout manualmente

        // CUADRO DE TEXTO
        cuadrado = new JTextField();
        cuadrado.setBounds(5, 5, 950, 100); // Dimensiones
        cuadrado.setEditable(false); // Deshabilitamos la edición manual
        cuadrado.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente grande
        add(cuadrado); // Añadimos el cuadro de texto

        // INICIALIZAMOS EL ARRAY DE LOS BOTONES
        botones = new JButton[17];

        // CONFIGURAMOS LOS BOTONES MANUALMENTE
        botones[0] = crearBoton("0", 230, 480, 200, 50);
        botones[1] = crearBoton("1", 20, 420, 200, 50);
        botones[2] = crearBoton("2", 230, 420, 200, 50);
        botones[3] = crearBoton("3", 440, 420, 200, 50);
        botones[4] = crearBoton("4", 20, 360, 200, 50);
        botones[5] = crearBoton("5", 230, 360, 200, 50);
        botones[6] = crearBoton("6", 440, 360, 200, 50);
        botones[7] = crearBoton("7", 20, 300, 200, 50);
        botones[8] = crearBoton("8", 230, 300, 200, 50);
        botones[9] = crearBoton("9", 440, 300, 200, 50);

        // Botones de operaciones ajustados a la derecha
        botones[10] = crearBoton("-", 800, 120, 150, 50);
        botones[11] = crearBoton("+", 800, 180, 150, 50);
        botones[12] = crearBoton("*", 800, 240, 150, 50);
        botones[13] = crearBoton("/", 800, 300, 150, 50);
        botones[14] = crearBoton("=", 650, 480, 300, 50);
        botones[15] = crearBoton("Borrar", 800, 420, 150, 50);
        botones[16] = crearBoton(".", 800, 360, 150, 50);

        // REGISTRAMOS LOS EVENTOS DE LOS BOTONES
        for (JButton boton : botones) {
            boton.addActionListener(this);
            add(boton);
        }
    }

    /** Método auxiliar para crear botones con propiedades personalizadas. */
    private JButton crearBoton(String texto, int x, int y, int width, int height) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, width, height); // Configuramos posición y tamaño
        boton.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente grande
        return boton;
    }

    /** Sobrescribimos paintComponent para personalizar el fondo o el diseño si es necesario. */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpia el panel antes de dibujar
    }

    /** Manejo de eventos: agrega el número del botón pulsado al cuadro de texto. */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton boton : botones) {
            if (boton != null && e.getSource() == boton) {
                String textoBoton = boton.getText();
    
                if (textoBoton.matches("\\d")) { // Si es un número
                    cuadrado.setText(cuadrado.getText() + textoBoton);
                } else if (textoBoton.equals(".")) { // Si es un punto decimal
                    String textoActual = cuadrado.getText();
                    // Dividimos el texto por espacios y verificamos el último número
                    String[] partes = textoActual.split(" ");
                    if (partes.length == 0 || !partes[partes.length - 1].contains(".")) {
                        // Solo añadimos el punto si el último número no tiene ya un punto
                        cuadrado.setText(textoActual + textoBoton);
                    }
                } else if (textoBoton.matches("[+\\-*/]")) { // Si es una operación
                    if (!operacionActual.isEmpty()) {
                        // Si ya hay una operación, calcula el resultado parcial
                        double segundoNumero = Double.parseDouble(cuadrado.getText().split(" ")[2]);
                        primerNumero = realizarOperacion(primerNumero, segundoNumero, operacionActual);
                    } else {
                        // Guarda el número ingresado como el primer número
                        primerNumero = Double.parseDouble(cuadrado.getText());
                    }
                    operacionActual = textoBoton;
                    cuadrado.setText(cuadrado.getText() + " " + textoBoton + " ");
                } else if (textoBoton.equals("=")) { // Botón igual
                    if (!operacionActual.isEmpty()) {
                        double segundoNumero = Double.parseDouble(cuadrado.getText().split(" ")[2]);
                        double resultado = realizarOperacion(primerNumero, segundoNumero, operacionActual);
                        cuadrado.setText(String.valueOf(resultado));
                        operacionActual = ""; // Reinicia la operación
                    }
                } else if (textoBoton.equals("Borrar")) { // Botón borrar
                    cuadrado.setText("");
                    operacionActual = "";
                    primerNumero = 0;
                }
                break;
            }
        }
    }
    


    /** Realiza la operación matemática especificada. */
    private double realizarOperacion(double num1, double num2, String operacion) {
        return switch (operacion) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num2 != 0 ? num1 / num2 : Double.NaN; // Manejo de división por cero
            default -> 0;
        };
    }
}