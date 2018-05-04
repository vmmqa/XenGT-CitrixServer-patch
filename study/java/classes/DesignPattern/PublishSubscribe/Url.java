import java.io.*;
import java.net.*;
public class Url implements Subscriber<String> {
    private Url () {}
    public static Url create () {
    return new Url();
}
public static void printUrlContent (String input) {
    try {
        URL url = new URL(input);
        BufferedReader in =
            new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
            in.close();
        } catch (Exception e) {
            System.out.println(" Error trying to read URL: " + e.getMessage());
    }
}
    public void getPublication (String data) {
        if (data.startsWith("http://"))
            printUrlContent(data);
    }
}
