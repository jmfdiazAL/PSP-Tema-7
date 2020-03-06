
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Servidor_Seguro_SSL {

    // Esté codigo crea el socket servidor seguro y está a la espera de que se conecte un cliente
    // pero si no se crea la clase del socket seguro del Cliente y se ejecuta para conectarse al mismo puerto del servidor 5000, 
    // este código estará siempre ejecutándose
    public static void main(String[] args) {

        // Declara objeto tipo Factory para crear socket SSL servidor
        SSLServerSocketFactory facto = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        // Declara un socket servidor seguro y un socket cliente seguro
        SSLServerSocket socketServidorSsl = null;
        SSLSocket socketSsl = null;
        // Flujo de entrada
        BufferedReader entrada = null;

        try {
            // Crea el socket servidor seguro
            socketServidorSsl = (SSLServerSocket) facto.createServerSocket(5000);

            // El servidor está a la espera de que se conecte un socket cliente seguro            
            socketSsl = (SSLSocket) socketServidorSsl.accept();

            System.out.println("Cliente Socket seguro conectado por el puerto 5000 al Servidor\n");

            // Crea canal seguro sobre el socket cliente seguro abierto
            entrada = new BufferedReader(new InputStreamReader(socketSsl.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Error de E/S al crear el socket o el fichero que maneja el socket\n Tipo error: \n" + ex + "\n");
            Logger.getLogger(Servidor_Seguro_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
