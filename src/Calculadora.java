import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/* Esta es una clase de la calculadora que representa la ventana y los márgenes de la calculadora. */
public class Calculadora {

    /* Esta es la clase que define el marco principal de la calculadora */
    public static class marcoCalculadora extends JFrame {

        private final int ANCHO_NORMAL;
        private final int ALTO_NORMAL = 600;

        /* Constructor del marco principal */
        public marcoCalculadora() {

            Toolkit ventana = Toolkit.getDefaultToolkit(); // Iniciamos una nueva instancia del Toolkit
            Dimension tamañoPantalla = ventana.getScreenSize(); // Obtiene el tamaño de la pantalla del dispositivo y lo
                                                                // guarda en un objeto de tipo Dimensión.
            ANCHO_NORMAL = tamañoPantalla.width / 2; // Variable que se iguala a la mitad del ancho de la pantalla.

            setSize(ANCHO_NORMAL, ALTO_NORMAL); // Establece el tamaño inicial de la ventana
            setLocationRelativeTo(null); // Centra la ventana
            setTitle("Calculadora del Aliexpress"); // Establece el nombre de la ventana

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
            setResizable(true); // Permite redimensionar (necesario para maximizar)

            addWindowListener(new Ventanita()); // Detecta eventos de la ventana
            addWindowStateListener(new Ventanita()); // Detecta cambios de estado como maximizar o minimizar

            // Evitar redimensionar manualmente, pero permitir maximizar
            addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                        // Bloquear tamaño manual si no está maximizado
                        setSize(ANCHO_NORMAL, ALTO_NORMAL);
                        setLocationRelativeTo(null); // Centrar después de restaurar
                    }
                }
            });

            add(new Lamina()); // Añade la lámina principal
        }
    }

    /*
     * Esta clase extiende la clase abstracta WindowAdapter y sobrescribe métodos
     * para reaccionar a eventos específicos
     */
    public static class Ventanita extends WindowAdapter {

        @Override
        public void windowActivated(WindowEvent e) { // Cuando la ventana gana el foco
            System.out.println("La ventana está abierta.");
        }

        @Override
        public void windowDeactivated(WindowEvent e) { // Cuando la ventana pierde el foco
            System.out.println("La ventana está cerrada.");
        }

        @Override
        public void windowIconified(WindowEvent e) { // Cuando la ventana es minimizada
            System.out.println("La ventana está minimizada.");
        }

        @Override
        public void windowDeiconified(WindowEvent e) { // Cuando la ventana es restaurada desde minimizada
            System.out.println("La ventana ha vuelto a estar visible.");
        }

        @Override
        public void windowStateChanged(WindowEvent e) { // Detecta cambios de estado de la ventana
            if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                System.out.println("La ventana está maximizada.");
            } else if ((e.getNewState() & JFrame.NORMAL) == JFrame.NORMAL) {
                System.out.println("La ventana está en tamaño normal.");
            }
        }
    }

    /* Método principal que ejecuta la aplicación */
    public static void main(String[] args) {
        marcoCalculadora marco1 = new marcoCalculadora(); // Crea una instancia de la calculadora
        marco1.setVisible(true); // Hace visible la ventana
    }
}
