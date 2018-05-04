public class PublishDemo
{
    public static void main(String[] args)
            {
                        System.out.println("Hello World!");
                    InputLoop il = InputLoop.create();
il.subscriber(Echo.create());
il.subscriber(Quit.create());
il.subscriber(Response.create("foo","bar"));
il.subscriber(Response.create("1+1","2"));
il.subscriber(Url.create());
il.loop();

                }
}

