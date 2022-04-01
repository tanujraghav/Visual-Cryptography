import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class TripleDES{


  static SecretKey secretKey = null;

  static Cipher cipher = null;

  static final String[] pixels = {"Red", "Green", "Blue"};

  static final PrintStream stdout = System.out;


  static{

    try{

      secretKey = KeyGenerator.getInstance("DESede").generateKey();
      cipher = Cipher.getInstance("DESede/ECB/NoPadding");

      for(String i: pixels){
        encrypt(i);
      }

    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e){
      e.printStackTrace();
    }

  }


  private static void encrypt(String str) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, IOException{

    Scanner scanner = new Scanner(new File("../assets/targettext_" + str + ".txt"));

    byte[] plaintext = new byte[1000000];
    for(int i=0; i < plaintext.length; i++){
      plaintext[i] = (byte) scanner.nextInt();
    }

    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] buff = cipher.doFinal(plaintext);

    System.setOut(new PrintStream(new File("../assets/3DES_" + str + ".txt")));
    for(byte elem: buff){
      System.out.print((elem & 0xff) + " ");
    }

    System.setOut(stdout);

  }
}