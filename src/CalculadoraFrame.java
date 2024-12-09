import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CalculadoraFrame extends JFrame {

    public CalculadoraFrame() {
        setTitle("Calculadora de AliExpress - Marcos Vidal González");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurar la pantalla en modo normal
        configurarPantallaNormal();  

        // Crea y añade la lámina principal
        Lamina lamina = new Lamina();
        add(lamina);

        // Maneja el redimensionamiento de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();  // Reorganiza los componentes al cambiar el tamaño de la ventana
            }
        });

        setVisible(true);  // Mostrar la ventana
    }

    /**
     * Configura la ventana para el estado de pantalla normal.
     * Ancho: Mitad de la pantalla.
     * Alto: 600px.
     * Centrada en la pantalla.
     */
    private void configurarPantallaNormal() {
        Toolkit pantalla = Toolkit.getDefaultToolkit(); // Accede a la pantalla
        Dimension tamanoPantalla = pantalla.getScreenSize(); // Tamaño de la pantalla
       
        int ancho = tamanoPantalla.width / 2; // Ancho: mitad de la pantalla
        int alto = 600; // Alto fijo de 600px

        setSize(ancho, alto);  // Define el tamaño de la ventana
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setResizable(true);  // Permite redimensionar
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraFrame::new);
    }
}
