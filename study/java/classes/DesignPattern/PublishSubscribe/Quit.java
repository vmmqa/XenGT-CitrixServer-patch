public class Quit implements Subscriber<String> {
    private Quit () {}
    public static Quit create () {
        return new Quit();
    }
    public void getPublication (String data) {
        if (data.equals("quit"))
            System.exit(0);
    }
}

