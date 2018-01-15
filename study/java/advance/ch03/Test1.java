import java.io.*;

public class Test1
{
    public static void main(String[] args)
    {
        try {
            String content = "Thank you for your fish.";

            File file = new File("new.txt");

            // create the file if doesn't exists
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        }
        catch(IOException e) {
            System.out.println("IO Problem");
        }
    }
}
