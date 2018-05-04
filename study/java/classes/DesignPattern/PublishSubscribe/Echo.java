public class Echo implements Subscriber<String> {
    private Echo () {}
    public static Echo create () {
        return new Echo();
    }
    public void getPublication (String data) {
        System.out.println("Got: " + data);
    }
}

