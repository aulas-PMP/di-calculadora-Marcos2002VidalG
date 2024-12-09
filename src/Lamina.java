import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/** CLASE PRINCIPAL DE LA CALCULADORA 
 * Hereda de JPanel y maneja eventos de botones y teclado
*/
public class Lamina extends JPanel implements ActionListener, KeyListener {

    // CUADROS DE TEXTO
    private JTextField cuadrado;                // Muestra el número actual o el resultado
    private JTextField operacionTexto;          // Muestra la operación en curso
    
    // BOTONES
    private JButton[] botonesNumericos;         // Botones de números y símbolos (0-9, , y =)
    private JButton[] botonesOperaciones;       // Botones de operaciones (+, -, *, /, Borrar)

    // VARIABLES INTERNAS
    private String operacionActual = "";        // Guarda la operación seleccionada
    private double primerNumero = 0;            // Primer número ingresado
    private String modoActual = "Teclado y Ratón";      // Controla si se usa teclado, ratón o ambos

     // FORMATO DECIMAL
    private final DecimalFormat formatoDecimal;     // Para mostrar números con ',' como separador decimal
    
    // CONSTRUCTOR
    public Lamina() {
        setLayout(new BorderLayout(5, 5));      // Margen de 5 píxeles entre componentes

        // CONFIGURACIÓN DEL FORMATO DECIMAL
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');      // Define ',' como separador decimal
        formatoDecimal = new DecimalFormat("#.##", simbolos);       // Formato a 2 decimales

        // CREACIÓN DE CUADROS DE TEXTO
        JPanel panelTextos = new JPanel(new GridLayout(2, 1, 5, 5));
        operacionTexto = crearCuadroTexto(false, new Font("Arial", Font.PLAIN, 20)); // Muestra la operación
        cuadrado = crearCuadroTexto(false, new Font("Arial", Font.BOLD, 30)); // Muestra el número actual
        panelTextos.add(operacionTexto);
        panelTextos.add(cuadrado);
        add(panelTextos, BorderLayout.NORTH); // Añade los cuadros arriba

        // CREACIÓN DE BOTONES NUMÉRICOS
        JPanel panelNumerico = new JPanel(new GridLayout(4, 3, 5, 5)); // Panel de botones de números
        String[] numeros = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ",", "=" };
        botonesNumericos = crearBotones(numeros, panelNumerico); // Crea y añade botones numéricos

        // CREACIÓN DE BOTONES DE OPERACIONES
        JPanel panelOperaciones = new JPanel(new GridLayout(4, 2, 5, 5)); // Panel de botones de operaciones
        String[] operaciones = { "+", "-", "*", "/", "Borrar", "Ratón", "Teclado", "Teclado y raton" };
        botonesOperaciones = crearBotones(operaciones, panelOperaciones); // Crea y añade botones de operaciones

        // AÑADE LOS PANELES AL PANEL PRINCIPAL
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 5, 5)); // Contenedor principal
        panelCentral.add(panelNumerico); // Panel de números a la izquierda
        panelCentral.add(panelOperaciones); // Panel de operaciones a la derecha
        add(panelCentral, BorderLayout.CENTER); // Añade el contenedor al centro

        // CONFIGURACIÓN DEL TECLADO
        setFocusable(true);  // Hace que el panel sea "enfocable"
        addKeyListener(this);  // Añade escucha del teclado
    }

     /**
     * Crea y configura un cuadro de texto.
     * @param editable Si el cuadro debe ser editable
     * @param font Fuente a aplicar al texto
     * @return Un JTextField configurado
     */
    private JTextField crearCuadroTexto(boolean editable, Font font) {
        JTextField cuadro = new JTextField();
        cuadro.setEditable(editable);   // Define si es editable
        cuadro.setFont(font);   // Aplica fuente personalizada
        cuadro.setHorizontalAlignment(JTextField.RIGHT);    // Alinea a la derecha
        return cuadro;
    }

    /**
     * Crea un arreglo de botones y los añade a un panel.
     * @param textos Textos de los botones
     * @param panel Panel donde se añadirán los botones
     * @return Un arreglo con los botones creados
     */
    private JButton[] crearBotones(String[] textos, JPanel panel) {
        JButton[] botones = new JButton[textos.length];
        for (int i = 0; i < textos.length; i++) {
            botones[i] = new JButton(textos[i]);    // Crea botón con texto
            botones[i].setFont(new Font("Arial", Font.BOLD, 20));     // Aplica fuente
            botones[i].addActionListener(this);     // Añade escucha de acción
            panel.add(botones[i]);      // Añade botón al panel
        }
        return botones;
    }

    /**
     * Maneja los clics de botones.
     * @param e Evento de acción
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String textoBoton = ((JButton) e.getSource()).getText();    // Obtiene texto del botón presionado
        switch (textoBoton) {
            case "Ratón" -> activarModo("Ratón", true);    // Activa modo "Ratón"
            case "Teclado y raton" -> activarModo("Teclado y Ratón", true);     // Activa ambos modos
            case "Teclado" -> activarModo("Teclado", false);    // Solo teclado
            default -> manejarEntrada(textoBoton);     // Procesa la entrada
        }
    }

     /**
     * Maneja la entrada del usuario.
     * @param textoBoton Texto del botón presionado
     */
    private void manejarEntrada(String textoBoton) {
        if (textoBoton.matches("[+\\-*/]")) { // Si es una operación
            if (operacionActual.isEmpty() && !cuadrado.getText().isEmpty()) {
                primerNumero = obtenerNumero(cuadrado.getText()); // Guarda primer número
                operacionActual = textoBoton; // Guarda operación actual
                actualizarOperacion(formatoDecimal.format(primerNumero) + " " + operacionActual);
                cuadrado.setText(""); // Limpia el cuadro de texto principal
            }
        } else if (textoBoton.matches("\\d")) { // Si es un número
            cuadrado.setText(cuadrado.getText() + textoBoton);
        } else if (textoBoton.equals(",") || textoBoton.equals(".")) { // Punto decimal
            if (!cuadrado.getText().contains(",")) { // Evita puntos dobles
                cuadrado.setText(cuadrado.getText() + ",");
            }
        } else if (textoBoton.equals("=")) { // Si es "="
            realizarCalculo();
        } else if (textoBoton.equals("Borrar")) { // Resetea la calculadora
            resetearCalculadora();
        }
    }

    /**
     * Realiza el cálculo actual.
     */
    private void realizarCalculo() {
        if (!cuadrado.getText().isEmpty() && !operacionActual.isEmpty()) {
            try {
                double segundoNumero = obtenerNumero(cuadrado.getText());
                double resultado = realizarOperacion(primerNumero, segundoNumero, operacionActual);
                mostrarResultado(resultado); // Muestra el resultado
                operacionTexto.setText(
                    formatoDecimal.format(primerNumero) + " " + operacionActual + " " +
                    formatoDecimal.format(segundoNumero) + " ="
                );
                operacionActual = ""; // Limpia la operación actual
                primerNumero = resultado; // Guarda el resultado para futuras operaciones
            } catch (ArithmeticException ex) {
                mostrarError("Error: División por 0");
            } catch (NumberFormatException ex) {
                mostrarError("Entrada no válida");
            }
        }
    }

    /**
     * Realiza una operación matemática.
     * @param num1 Primer número
     * @param num2 Segundo número
     * @param operacion Operación a realizar
     * @return Resultado de la operación
     */
    private double realizarOperacion(double num1, double num2, String operacion) {
        return switch (operacion) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new ArithmeticException("División por 0");
                yield num1 / num2;
            }
            default -> 0;
        };
    }

    private double obtenerNumero(String texto) {
        return Double.parseDouble(texto.replace(",", "."));
    }

    private void mostrarResultado(double resultado) {
        cuadrado.setText(formatoDecimal.format(resultado));
        cuadrado.setForeground(resultado < 0 ? Color.RED : Color.BLACK);
    }

    private void mostrarError(String mensaje) {
        cuadrado.setText(mensaje);
        cuadrado.setForeground(Color.RED);
    }

    private void resetearCalculadora() {
        cuadrado.setText("");
        operacionTexto.setText("");
        operacionActual = "";
        primerNumero = 0;
    }

    private void activarModo(String modo, boolean habilitarBotones) {
        modoActual = modo;
        habilitarBotones(habilitarBotones);
        requestFocusInWindow();
    }

    private void habilitarBotones(boolean estado) {
        for (JButton boton : botonesNumericos) boton.setEnabled(estado);
        for (JButton boton : botonesOperaciones) {
            if (!boton.getText().matches("Ratón|Teclado y raton|Teclado")) {
                boton.setEnabled(estado);
            }
        }
    }

    /**
 * Actualiza el cuadro de texto donde se muestra la operación actual.
 * 
 * @param texto El texto de la operación a mostrar.
 */
private void actualizarOperacion(String texto) {
    operacionTexto.setText(texto);
}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            manejarEntrada("Borrar");
            return;
        }
        if (modoActual.equals("Teclado") || modoActual.equals("Teclado y Ratón")) {
            switch (keyCode) {
                case KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2,
                     KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5,
                     KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8,
                     KeyEvent.VK_NUMPAD9 -> manejarEntrada(String.valueOf(keyCode - KeyEvent.VK_NUMPAD0));
                case KeyEvent.VK_ADD -> manejarEntrada("+");
                case KeyEvent.VK_SUBTRACT -> manejarEntrada("-");
                case KeyEvent.VK_MULTIPLY -> manejarEntrada("*");
                case KeyEvent.VK_DIVIDE -> manejarEntrada("/");
                case KeyEvent.VK_DECIMAL -> manejarEntrada(",");
                case KeyEvent.VK_ENTER -> manejarEntrada("=");
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
