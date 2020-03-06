package cifrar_clavepublicarsa;

import java.io.IOException;
import java.security.*;
import javax.crypto.*;

//Encriptar y desencriptar un texto mediante clave pública RSA
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Crear clave pública y privada");

        // Crea e inicializa el generador de claves RSA (Rivest, Shamir y Adleman)
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        // tamaño de la clave
        keyGen.initialize(512);

        // Generar el par de claves asimétricas (pública y privada)
        KeyPair clavesRSA = keyGen.generateKeyPair();

        // obtiene clave privada
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        // obtiene clave pública
        PublicKey clavePublica = clavesRSA.getPublic();

        // muestra las claves generadas
        System.out.println("clavePublica: " + clavePublica);
        System.out.println("clavePrivada: " + clavePrivada);

        //texto a encriptar o cifrar => lo pasa a vector de bytes con getBytes()
        byte[] bufferClaro = "Este es el mensaje secreto a cifrar\n".getBytes();

        //Crea cifrador RSA
        Cipher cifrador = Cipher.getInstance("RSA");

        //Pone cifrador en modo ENCRIPTACIÓN utilizando la clave pública
        cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
        System.out.println("Cifrar con clave pública el Texto:");
        mostrarBytes(bufferClaro);

        // obtiene todo el texto cifrado => fijate que no hace falta usar update()
        // aunque si quiere se puede hacer en dos pasos con el update():
        // paso 1.) byte[] bufferCifrado = cifrador.update(bufferClaro);
        // paso 2.) bufferCifrado = cifrador.doFinal();
        byte[] bufferCifrado = cifrador.doFinal(bufferClaro);
        System.out.println("Texto CIFRADO");
        mostrarBytes(bufferCifrado); //muestra texto cifrado
        System.out.println("\n_______________________________");

        //Pone cifrador en modo DESENCRIPTACIÓN utilizando la clave privada
        cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
        System.out.println("Descifrar con clave privada");

        //obtiene el texto descifrado
        bufferClaro = cifrador.doFinal(bufferCifrado);
        System.out.println("Texto DESCIFRADO");
        mostrarBytes(bufferClaro);//muestra texto descifrado
        System.out.println("\n_______________________________");
    }

    public static void mostrarBytes(byte[] buffer) throws IOException {
        System.out.write(buffer);
    }
}
