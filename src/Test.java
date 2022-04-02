import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Test {
  static final int WIDTH = 1000, HEIGHT = 1000;
  static final File INFILE = new File("../assets/target.jpg");

  static final String[] COLORS = {"Red", "Green", "Blue"};

  static final PrintStream stdout = System.out;

  static long tmp = 0;

  public static void main(String[] args) throws IOException {
    extractImageData();

    // DES Encryption
    tmp = System.nanoTime();

    new DES();

    tmp = System.nanoTime() - tmp;
    System.out.println("DES Algorithm: " + tmp + " ns");

    generateImage("DES", "cipher", "en");
    generateImage("DES", "plain", "de");

    // 3DES Encryption
    tmp = System.nanoTime();

    new TripleDES();

    tmp = System.nanoTime() - tmp;
    System.out.println("3DES Algorithm: " + tmp + " ns");

    generateImage("3DES", "cipher", "en");
    generateImage("3DES", "plain", "de");

    // AES Encryption
    tmp = System.nanoTime();

    new AES();

    tmp = System.nanoTime() - tmp;
    System.out.println("AES Algorithm: " + tmp + " ns");

    generateImage("AES", "cipher", "en");
    generateImage("AES", "plain", "de");
  }

  private static void extractImageData() throws IOException {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    image = ImageIO.read(INFILE);

    int[][] pixelData = new int[COLORS.length][WIDTH * HEIGHT];
    int buff;

    for(int i = 0; i < WIDTH; i++) {
      for(int j = 0; j < HEIGHT; j++) {
        buff = image.getRGB(i, j);
        for(int k = 0; k < COLORS.length; k++) {
          pixelData[k][i * WIDTH + j] = (buff >> 8 * (2 - k)) & 255;
        }
      }
    }

    for(int i = 0; i < COLORS.length; i++) {
      toFile(COLORS[i], pixelData[i]);
    }

    System.setOut(stdout);
  }

  private static void toFile(String clr, int[] data) throws IOException {
    System.setOut(new PrintStream(new File("../assets/plaintext_" + clr + ".txt")));
    for(int elem: data) {
      System.out.print(elem+" ");
    }
  }

  private static void generateImage(String algo, String type, String status) throws IOException {
    Scanner[] scanner = new Scanner[3];
    for(int i = 0; i < COLORS.length; i++) {
      scanner[i] = new Scanner(new File("../assets/" + algo + "/" + type + "text_" + COLORS[i] + ".txt"));
    }

    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    for(int i = 0; i < WIDTH; i++) {
      for(int j = 0; j < HEIGHT; j++) {        
        image.setRGB(i, j, (scanner[0].nextInt() << 16) | (scanner[1].nextInt() << 8) | scanner[2].nextInt());
      }
    }

    ImageIO.write(image, "jpg", new File("../assets/" + algo + "/" + status + "crypted.jpg"));
  }
}
