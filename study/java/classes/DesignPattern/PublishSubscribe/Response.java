public class Response implements Subscriber<String> {
    private String ifthis;
    private String thenthat;
    private Response (String it, String tt) {
        ifthis = it;
        thenthat = tt;
    }

    public static Response create (String it, String tt) {
        return new Response(it,tt);
    }
    public void getPublication (String data) {
    if (data.equals(ifthis))
        System.out.println(thenthat);
    }
}


