package generarclaves;
//bibliotecas necesarias
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    //Programa que crea una pareja de claves (pública y privada) y las muestra
    public static void main(String[] args) {
        //Asigna al objeto claves de tipo keyPair el par de claves generadas
        //por el método GeneraParejaClave()
        KeyPair claves = GeneraParejaClave();

        //Imprime el valor de las claves (Key) generadas en diferentes formatos
        System.out.println("Algoritmo Kprivada: " + claves.getPrivate().getAlgorithm());
        System.out.println("Codificación Kprivada: " + claves.getPrivate().getFormat());
        System.out.println("Bytes Kprivada: " + claves.getPrivate().toString());
        System.out.println("Algoritmo Kpública: " + claves.getPublic().getAlgorithm());
        System.out.println("Codificación Kpública: " + claves.getPublic().getFormat());
        System.out.println("Bytes Kpública: " + claves.getPublic().toString());
    }

    //Método que genera una clave tipo KeyPair (uan pareja de claves)
    public static KeyPair GeneraParejaClave() {
        KeyPair claves = null;
        try {                        
            //Crea el objeto para generar un par de claves mediante RSA
            KeyPairGenerator genera = KeyPairGenerator.getInstance("RSA");
            genera.initialize(512); //asigna tamaño de la clave
            claves = genera.generateKeyPair(); //genera la pareja de claves            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);            
        }        
        return claves;
    }

}
