import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA{


  static KeyPairGenerator keyPairGenerator = null;

  static KeyPair keyPair = null;

  static Cipher cipher = null;

  static final String[] pixels = {"Red", "Green", "Blue"};

  static final PrintStream stdout = System.out;


  static{

    try{

      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(1024);

      keyPair = keyPairGenerator.generateKeyPair();
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

      for(String i: pixels){
        encrypt(i);
      }

    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e){
      e.printStackTrace();
    }

  }


  private static void encrypt(String str) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, IOException{

    Scanner scanner = new Scanner(new File("../assets/targettext_" + str + ".txt"));

    byte[] ciphertext = new byte[1000000];

    for(int j = 0; j < 10000; j++){

      byte[] plaintext = new byte[100];
      for(int i = 0; i < plaintext.length; i++){
        plaintext[i] = (byte) scanner.nextInt();
      }

      cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
      byte[] buff = cipher.doFinal(plaintext);

      for(int i = 0; i < plaintext.length; i++){
        ciphertext[100*j + i] = buff[i];
      }

    }

    System.setOut(new PrintStream(new File("../assets/RSA_" + str + ".txt")));
    for(byte elem: ciphertext){
      System.out.print((elem & 0xff) + " ");
    }

    System.setOut(stdout);

  }
}