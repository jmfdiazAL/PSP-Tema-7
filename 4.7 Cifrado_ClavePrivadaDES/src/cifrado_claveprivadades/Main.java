/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado_claveprivadades;

import java.security.*; //JCA
import javax.crypto.*; //JCE
import java.io.*; //ficheros
import java.util.logging.Level;
import java.util.logging.Logger;

//Programa que encripta y desencripta un fichero
//mediante clave privada o simétrica utilizando el algoritmo DES
public class Main {

    public static void main(String[] Args) {
        SecretKey clave = null;

        try {
            // declara e incializa objeto tipo clave secreta
            
            /* OJO para que funciona el fichero a cifrar es sin extensión, no vale fichero.txt */

            //llama a los métodos que encripta/desencripta un fichero
            /* Encriptar en Windows, debe existir el fichero => c:\\cripto\\fichero
            
             //Llama al método que encripta el fichero que se pasa como parámetro
             clave = cifrarFichero("c:\\cripto\\fichero");
             //Llama la método que desencripta el fichero pasado como primer parámetro
             descifrarFichero("c:\\cripto\\fichero.cifrado", clave,
             "c:\\cripto\\fichero.descifrado"); */
            /* Encriptar en Linux => debe existir el fichero => /home/ramon/fichero */
            //Llama al método que encripta el fichero que se pasa como parámetro
            clave = cifrarFichero("/home/ramon/fichero");

            //Llama la método que desencripta el fichero pasado como primer parámetro
            descifrarFichero("/home/ramon/fichero.cifrado", clave, "/home/ramon/fichero.descifrado");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //método que encripta el fichero que se pasa como parámetro
    //devuelve el valor de la clave privada utilizada en encriptación
    //El fichero encriptado lo deja en el archivo de nombre fichero.cifrado
    //en el mismo directorio
    private static SecretKey cifrarFichero(String file) throws NoSuchAlgorithmException,
            NoSuchPaddingException, FileNotFoundException, IOException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {

        FileInputStream fentrada = null; //fichero de entrada
        FileOutputStream fsalida = null; //fichero de salida
        int bytesLeidos;

        //1. Crear e inicializar clave
        System.out.println("1.-Genera clave DES");

        // crea un objeto para generar la clave usando algoritmo Triple DES (112 bits longitud clave)
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");

        // Se podría haber usado DES de esta forma (pero tiene menos seguridad => 56 bits longitud clave)
        // Cipher cifrador = Cipher.getInstance("DES");
        
        // se indica el tamaño de la clave
        keyGen.init(112);

        // genera la clave privada
        SecretKey clave = keyGen.generateKey();

        System.out.println("Clave");
        // muestra la clave
        mostrarBytes(clave.getEncoded());
        System.out.println();

        // Se Crea el objeto Cipher para cifrar, utilizando el algoritmo Triple DES
        Cipher cifrador = Cipher.getInstance("DESede");

        // Se inicializa el cifrador en modo CIFRADO o ENCRIPTACIÓN
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        System.out.println("2.- Cifrar con Triple DES el fichero: " + file
                + ", y dejar resultado en " + file + ".cifrado");

        //declaración  de objetos
        byte[] buffer = new byte[1000]; //array de bytes para leer del fichero
        byte[] bufferCifrado;  // vector con los bytes cifrados del fichero
        
        fentrada = new FileInputStream(file); //objeto fichero de entrada
        fsalida = new FileOutputStream(file + ".cifrado"); //fichero de salida

        //lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
        bytesLeidos = fentrada.read(buffer, 0, 1000);

        //mientras no se llegue al final del fichero de entrada
        while (bytesLeidos != -1) {
            // pasa texto claro al cifrador y lo cifra, asignándolo a bufferCifrado
            bufferCifrado = cifrador.update(buffer, 0, bytesLeidos);

            // Graba el texto cifrado en fichero de salida
            fsalida.write(bufferCifrado);

            bytesLeidos = fentrada.read(buffer, 0, 1000);
        }

        // Completa el cifrado
        bufferCifrado = cifrador.doFinal();

        // Graba el final del texto cifrado, si lo hay
        fsalida.write(bufferCifrado);

        //Cierra ficheros
        fentrada.close();
        fsalida.close();

        return clave;
    }

    //método que desencripta el fichero pasado como primer parámetro file1
    //pasándole también la clave privada que necesita para desencriptar, key
    //y deja el fichero desencriptado en el tercer parámetro file2
    private static void descifrarFichero(String file1, SecretKey key, String file2) throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        FileInputStream fe = null; //fichero de entrada
        FileOutputStream fs = null; //fichero de salida
        int bytesLeidos;
        
        // escoger como algoritmo para descifrar el Triple DES
        Cipher cifrador = Cipher.getInstance("DESede");

        //3.- Poner cifrador en modo DESCIFRADO o DESENCRIPTACIÓN
        cifrador.init(Cipher.DECRYPT_MODE, key);
        System.out.println("3.- Descifrar con Triple DES el fichero: " + file1 + ", y dejar en  " + file2);

        fe = new FileInputStream(file1);
        fs = new FileOutputStream(file2);
        byte[] bufferClaro;
        byte[] buffer = new byte[1000]; //array de bytes

        // lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
        bytesLeidos = fe.read(buffer, 0, 1000);

        // mientras no se llegue al final del fichero EOF
        while (bytesLeidos != -1) {
            //pasa texto cifrado al cifrador y lo descifra, asignándolo a bufferClaro
            bufferClaro = cifrador.update(buffer, 0, bytesLeidos);

            //Graba el texto claro en fichero
            fs.write(bufferClaro);

            bytesLeidos = fe.read(buffer, 0, 1000);
        }

        //Completa el descifrado
        bufferClaro = cifrador.doFinal();

        //Graba el final del texto claro, si lo hay
        fs.write(bufferClaro);

        //cierra archivos
        fe.close();
        fs.close();
    }

    //método que muestra bytes
    public static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
}
