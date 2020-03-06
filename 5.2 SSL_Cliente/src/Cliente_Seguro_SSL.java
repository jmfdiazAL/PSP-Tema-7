
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cliente_Seguro_SSL {

    public static void main(String[] args) {
        try {
            //Declara un objeto tipo Factory para crear sockets SSL
            SSLSocketFactory facty = (SSLSocketFactory) SSLSocketFactory.getDefault();

            //Crea un cliente del socket seguro
            SSLSocket socketSsl = (SSLSocket) facty.createSocket("localhost", 5000);

            //Consola que lee la entrada del usuario
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

            //Canal de comunicación con el servidor
            BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socketSsl.getOutputStream()));

            System.out.println("Cliente Socket seguro se ha conectado por el puerto 5000 al Servidor\n");

        } catch (IOException ex) {
            System.out.println("\nError de E/S en el socket cliente seguro\n Tipo error:\n" + ex + "\n");
            Logger.getLogger(Cliente_Seguro_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
