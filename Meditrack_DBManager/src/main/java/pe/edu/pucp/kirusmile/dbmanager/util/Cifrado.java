package pe.edu.pucp.kirusmile.dbmanager.util;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cifrado {

    private static final String llave = "KiruSmile123456";

    private static SecretKeySpec crearClave(String llave) throws Exception {
        byte[] key = llave.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // 128 bits para AES
        return new SecretKeySpec(key, "AES");
    }

    public static String cifrar(String texto) {
        try {
            SecretKeySpec secretKey = crearClave(llave);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = cipher.doFinal(texto.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String descifrar(String texto) {
        try {
            SecretKeySpec secretKey = crearClave(llave);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decoded = Base64.getDecoder().decode(texto);
            byte[] decrypted = cipher.doFinal(decoded);

            return new String(decrypted, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
