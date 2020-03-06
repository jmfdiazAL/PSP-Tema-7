/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp07_cont_r25_resumenmensajessha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ramon
 */
public class PSP07_CONT_R25_ResumenMensajesSHA1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Crear el objeto para el resumen con el algoritmo SHA1
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            String texto = "Texto para el mensaje ejemplo SHA1";

            //obtiene el resumen
            sha1.update(texto.getBytes());

            //completa la generación del resumen
            byte[] resumen = sha1.digest();

            System.out.println("El tamaño en bytes del resumen es: " + resumen.length + "\n");

            //muestra el resumen byte a byte
            for (int k = 0; k < resumen.length; k++) {
                System.out.println("(" + resumen[k] + ")");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
