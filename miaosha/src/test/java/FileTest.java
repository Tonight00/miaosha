import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author: backkom
 * @Date: 2020/9/26 22:38
 */
public class FileTest
{
    //Files && File
    public static void main (String[] args) throws IOException
    {
        File t = new File("D:/timg.jpg");
        System.out.println(t.toString());
        System.out.println(t.exists());
        byte[]bytes = Files.readAllBytes(t.toPath());
        System.out.write(bytes);
    }
}
