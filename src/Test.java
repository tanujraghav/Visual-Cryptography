import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class Test{


  static final int WIDTH = 1000, HEIGHT = 1000;

  static final File INFILE = new File("../assets/target.jpg");


  public static void main(String args[]) throws IOException{

    extractImageData();

  }
  

  public static void extractImageData() throws IOException{

    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    try {
      image = ImageIO.read(INFILE);
    } catch(IOException e){
      e.printStackTrace();
    }

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
    for(int elem:pixelData[0]){
      System.out.print(elem + " ");
    }

    System.setOut(new PrintStream(new File("../assets/targettext_Green.txt")));
    for(int elem:pixelData[1]){
      System.out.print(elem + " ");
    }

    System.setOut(new PrintStream(new File("../assets/targettext_Blue.txt")));
    for(int elem:pixelData[2]){
      System.out.print(elem + " ");
    }

    System.setOut(stdout);

  }


}