import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/* Esta es una clase de la calculadora que representa la ventana y los margenes de la calculadora. */
public class Calculadora {
    public static class marcoCalculadora extends JFrame {
        public marcoCalculadora(){

            Toolkit ventana = Toolkit.getDefaultToolkit(); // Iniciamos una nueva estancia del Toolkit
            Dimension tamañoPantalla = ventana.getScreenSize(); // Obtiene el tamaño de la pantalla del dipositivo y lo guarda en un objeto de tipo Dimensión.
            int ancho = tamañoPantalla.width; // Variable que se iguala al ancho de la pantalla.

            setSize(ancho/2,600); // Cambia el ancho y el alto de la pantalla, hace que el ancho sea la mitad de la pantalla y el a
            setLocationRelativeTo(null); //Centra la pantalla
            setResizable(false); // Hace que no se pueda redimensionar la pantalla.
            setTitle("Calculadora del Aliexpress"); // Establece el nombre de la ventana.
            addWindowListener(new Ventanita()); // Detecta eventos relacionados con la vetana, abrirse, cerrarse, minimizarse...
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establece lo que pasa cuando se cierra una ventana
        
            add(new Lamina());
        }
    }
    
    
    
    /* Esta clase extiende la clase abstracta WindowsAdapter y sobrescribe 3 metodos para reaccionar a eventos especificos */
    public static class Ventanita extends WindowAdapter{

        public void windowActivated(WindowEvent e){ // Cuando gana el foco imprime la ventana esta abierta.
            System.out.println("La ventana está abierta.");
        }
        
        public void windowDeactivated(WindowEvent e){ // Cuando gana el foco imprime la ventana esta cerrada.
            System.out.println("La ventana está cerrada.");
        }
        
        public void windowIconified(WindowEvent e){ // Cuando gana el foco imprime la ventana esta minimizada.
            System.out.println("La ventana está minimizada.");
        }
    }
    
    
    
    
    public static void main(String[] args){
        marcoCalculadora marco1 = new marcoCalculadora();
        marco1.setVisible(true);
    }
}
