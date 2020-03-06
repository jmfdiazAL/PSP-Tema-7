/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firmadigital;

//bibliotecas necesarias
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        String texto = "texto de prueba para ser firmado";
        String texto2 = "texto de PRUEBA para ser firmado";

        // generar las llaves pública y privada que necesita la firma digital
        KeyPair clave = generarClaves();

        // realizar la firma digital y almacenarla en textoFirmado
        byte[] textoFirmado = hacerFirma(texto.getBytes(), clave.getPrivate());

        // verificar si la firma es correcta o no comparando el texto original y el firmado con la clave pública
        if (verificarFirma(texto.getBytes(), clave.getPublic(), textoFirmado)) {
            System.out.println("Firma realizada y verificada correctamente");
        } else {
            System.out.println("Firma incorrecta");
        }

        // verificar si la firma es correcta o no comparando el texto original y el firmado con la clave pública
        if (verificarFirma(texto2.getBytes(), clave.getPublic(), textoFirmado)) {
            System.out.println("Firma realizada y verificada correctamente");
        } else {
            System.out.println("Firma incorrecta");
        }

    }

    //método que genera una pareja de claves (pública y privada)
    //que se utilizarán en la firma digital
    public static KeyPair generarClaves() {
        //inicializa el objeto claves, tipo KeyPair, a null
        KeyPair claves = null;
        try {
            //Indica el algoritmo a utilizar en la generación de claves
            KeyPairGenerator generador = KeyPairGenerator.getInstance("DSA");
            //asigna la pareja de claves generadas al objeto tipo KeyPair, claves
            claves = generador.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //retorna un objeto tipo KeyPair
        return claves;
    }

    //método que realiza la firma digital del texto o datos y la devuelve
    public static byte[] hacerFirma(byte[] datos, PrivateKey clave) {
        byte[] firmado = null;

        try {
            //crea el objeto tipo Signature con algoritmo DSA
            Signature firma = Signature.getInstance("DSA");
            //inicializa la firma con la clave privada a utilizar
            firma.initSign(clave);
            //obtiene el resumen del mensaje
            firma.update(datos);
            //obtiene la firma digital
            firmado = firma.sign();

            //devuelve la firma digital            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return firmado;
    }

    //Método que verifica la firma digital, devolviendo:
    //false, si la firma no es correcta o se produce una excepción
    //verdadero, si la firma es correcta
    public static boolean verificarFirma(byte[] texto, PublicKey clave_publica, byte[] textoFirmado) {
        boolean resultado = false;

        try {
            //crea el objeto tipo Signature con algoritmo DSA
            Signature firma = Signature.getInstance("DSA");
            //verifica la clave pública
            firma.initVerify(clave_publica);
            //actualiza el resumen de mensaje
            firma.update(texto);
            //devuelve el resultado de la verificación
            resultado = firma.verify(textoFirmado);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }
}
