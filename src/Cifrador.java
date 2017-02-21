import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * Created by Raquel on 15/02/17.
 */
public class Cifrador {
    String almacen = "/home/usuario/.keystore";
    char[] passAlmacen = "usuario".toCharArray();
    char[] passClaveP = "usuario".toCharArray();
    String alias = "raquelkey";


    public Cifrador() {}

    public Cifrador(String almacen, String passAlmacen, String passClaveP, String alias) {
        this.almacen = almacen;
        this.passAlmacen = passAlmacen.toCharArray();
        this.passClaveP = passClaveP.toCharArray();
        this.alias = alias;
    }

    public String cifrar(String frase) {
        String fraseC = null;
        try(
                FileInputStream fis=new FileInputStream(almacen)
        )

        {
            KeyStore ks=KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passAlmacen);
            PublicKey pubKeyPrueba=ks.getCertificate(alias).getPublicKey();

            //Cipher cifrado = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher cifrado=Cipher.getInstance(pubKeyPrueba.getAlgorithm());
            cifrado.init(Cipher.ENCRYPT_MODE,pubKeyPrueba);

            //System.out.println("ESTO BORRALO: "+pubKeyPrueba.getAlgorithm());
            byte[] datos = frase.getBytes("UTF8");
            byte[] datos1 = cifrado.doFinal(datos);

            //pasamos datos1 a base64 y lo guardamos en passC;
            fraseC = Base64.getEncoder().encodeToString(datos1);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | KeyStoreException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return fraseC;
    }
    public String descifrar(String frase) {
        String fraseDes = null;
        try (
                FileInputStream fis = new FileInputStream(almacen)
        ) {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passAlmacen);
            System.out.println("Almacen abierto, extrayendo clave privada...");
            System.out.println("Contraseña de clave privada: " + String.valueOf(passClaveP));
            PrivateKey pk = (PrivateKey) ks.getKey(alias, passClaveP);
            Cipher cifrado = Cipher.getInstance(pk.getAlgorithm());
            cifrado.init(Cipher.DECRYPT_MODE, pk);
            System.out.println("En Base64 la clave es: " + frase + "\n\n");
            byte[] textBytes = Base64.getDecoder().decode(frase);
            fraseDes = new String(cifrado.doFinal(textBytes));

        } catch (NoSuchPaddingException | IOException | IllegalBlockSizeException | InvalidKeyException | KeyStoreException | CertificateException | NoSuchAlgorithmException | BadPaddingException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return fraseDes;
    }


}
