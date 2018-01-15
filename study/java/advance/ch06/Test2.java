import java.util.*;

public class Test2
{
    public static void main(String[] args)
    {
        List<String> l1 = new ArrayList<String>();
        l1.add("good");
        l1.add("bad");
        l1.add("shit");
        l1.remove(0);
        System.out.println(l1.get(1));
        System.out.println(l1.size());
    }
}
