import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Test{


  static final int WIDTH = 1000, HEIGHT = 1000;

  static final File INFILE = new File("../assets/target.jpg");


  public static void main(String args[]) throws IOException{

    long flag;

    extractImageData();

    // DES Encryption
    flag = System.nanoTime();

    new DES();

    flag = System.nanoTime() - flag;
    System.out.println("DES Algorithm: " + flag + "ns");

    generateImage("DES");

    // 3DES Encryption
    flag = System.nanoTime();

    new TripleDES();

    flag = System.nanoTime() - flag;
    System.out.println("3DES Algorithm: " + flag + "ns");

    generateImage("3DES");

    // AES Encryption
    flag = System.nanoTime();

    new AES();

    flag = System.nanoTime() - flag;
    System.out.println("AES Algorithm: " + flag + "ns");

    generateImage("AES");

    // RSA Encryption
    flag = System.nanoTime();

    new RSA();

    flag = System.nanoTime() - flag;
    System.out.println("RSA Algorithm: " + flag + "ns");

    generateImage("RSA");

  }
  

  private static void extractImageData() throws IOException{

    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    image = ImageIO.read(INFILE);

    int pixelData[][] = new int[3][WIDTH * HEIGHT], buff;

    for(int i = 0; i < WIDTH; i++){
      for(int j = 0; j < HEIGHT; j++){

        buff = image.getRGB(i, j);

        pixelData[0][i*WIDTH+j] = (buff >> 16) & 255;
        pixelData[1][i*WIDTH+j] = (buff >> 8) & 255;
        pixelData[2][i*WIDTH+j] = buff & 255;

      }
    }

    PrintStream stdout = System.out;

    System.setOut(new PrintStream(new File("../assets/targettext_Red.txt")));
    for(int elem: pixelData[0]){
      System.out.print(elem + " ");
    }

    System.setOut(new PrintStream(new File("../assets/targettext_Green.txt")));
    for(int elem: pixelData[1]){
      System.out.print(elem + " ");
    }

    System.setOut(new PrintStream(new File("../assets/targettext_Blue.txt")));
    for(int elem: pixelData[2]){
      System.out.print(elem + " ");
    }

    System.setOut(stdout);

  }


  private static void generateImage(String flag) throws IOException{

    Scanner buffer[] = new Scanner[3];

    buffer[0] = new Scanner(new File("../assets/" + flag + "_Red.txt"));
    buffer[1] = new Scanner(new File("../assets/" + flag + "_Green.txt"));
    buffer[2] = new Scanner(new File("../assets/" + flag + "_Blue.txt"));

    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    for(int i = 0; i < WIDTH; i++){
      for(int j = 0; j < HEIGHT; j++){        
        image.setRGB(i, j, (buffer[0].nextInt() << 16) | (buffer[1].nextInt() << 8) | buffer[2].nextInt());
      }
    }

    ImageIO.write(image, "jpg", new File("../assets/" + flag + ".jpg"));

  }


}