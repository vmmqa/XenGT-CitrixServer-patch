import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirCreate {

    public static void main(String... args) throws IOException {
        Path path = Files.createTempDirectory(Paths.get("/data"),"myFile");
        System.out.println(path);
        System.out.println(path.toString());
        System.out.println(path.toAbsolutePath().toString());
        boolean b = Files.isDirectory(path);
        System.out.println(b);
    }
}
