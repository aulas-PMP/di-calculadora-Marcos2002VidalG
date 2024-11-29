import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Lamina extends JPanel implements ActionListener {
    private JTextField cuadrado; // Cuadro de texto
    private JButton[] botones;  // Array de botones
    private String operacionActual = ""; // Operación actual
    private double primerNumero = 0; // Primer número ingresado
    private String modoActual = "Teclado y Ratón"; // Modo inicial por defecto

    public Lamina() {
        setLayout(null); // Configuramos el layout manualmente

        // Cuadro de texto
        cuadrado = new JTextField();
        cuadrado.setBounds(5, 5, 950, 100); // Dimensiones
        cuadrado.setEditable(false); // Deshabilitamos la edición manual
        cuadrado.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente grande
        add(cuadrado); // Añadimos el cuadro de texto

        // Inicializamos el array de botones
        botones = new JButton[20];

        // Configuramos los botones manualmente
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
        botones[15] = crearBoton("Borrar", 20, 120, 150, 50);
        botones[16] = crearBoton(".", 465, 480, 150, 50);

        // Modos de la calculadora
        botones[17] = crearBoton("Ratón", 635, 120, 150, 50);
        botones[18] = crearBoton("Teclado y raton", 185, 120, 270, 50);
        botones[19] = crearBoton("Teclado", 470, 120, 150, 50);

        // Registramos los eventos de los botones
        for (JButton boton : botones) {
            boton.addActionListener(this);
            add(boton);
        }

        // Habilitar el uso del teclado
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (modoActual.equals("Ratón")) return; // Ignora entradas de teclado en modo Ratón

                char tecla = e.getKeyChar();
                if (Character.isDigit(tecla)) {
                    cuadrado.setText(cuadrado.getText() + tecla);
                } else if (tecla == '+' || tecla == '-' || tecla == '*' || tecla == '/') {
                    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(tecla)));
                } else if (tecla == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "="));
                } else if (tecla == KeyEvent.VK_BACK_SPACE) {
                    cuadrado.setText("");
                }
            }
        });
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

                // Manejo de modos
                if (textoBoton.equals("Ratón")) {
                    modoActual = "Ratón";
                    cuadrado.setText("Modo: Solo Ratón");
                    return;
                } else if (textoBoton.equals("Teclado y raton")) {
                    modoActual = "Teclado y Ratón";
                    cuadrado.setText("Modo: Teclado y Ratón");
                    return;
                } else if (textoBoton.equals("Teclado")) {
                    modoActual = "Teclado";
                    cuadrado.setText("Modo: Solo Teclado");
                    return;
                }

                // Ignorar eventos según el modo actual
                if (modoActual.equals("Teclado") && !(e.getSource() instanceof JButton)) return;

                // Lógica principal
                if (textoBoton.matches("\\d")) { // Si es un número
                    cuadrado.setText(cuadrado.getText() + textoBoton);
                } else if (textoBoton.equals(".")) { // Si es un punto decimal
                    String textoActual = cuadrado.getText();
                    if (!textoActual.contains(".")) {
                        cuadrado.setText(textoActual + textoBoton);
                    }
                } else if (textoBoton.matches("[+\\-*/]")) { // Si es una operación
                    primerNumero = Double.parseDouble(cuadrado.getText());
                    operacionActual = textoBoton;
                    cuadrado.setText("");
                } else if (textoBoton.equals("=")) { // Botón igual
                    double segundoNumero = Double.parseDouble(cuadrado.getText());
                    double resultado = realizarOperacion(primerNumero, segundoNumero, operacionActual);
                    cuadrado.setText(String.valueOf(resultado));
                    operacionActual = "";
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
