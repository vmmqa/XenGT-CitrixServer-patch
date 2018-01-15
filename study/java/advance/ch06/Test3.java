import java.util.*;

public class Test3
{
    public static void main(String[] args)
    {
        Set<Integer> s1 = new HashSet<Integer>();
        s1.add(4);
        s1.add(5);
        s1.add(4);
        s1.remove(5);
        System.out.println(s1);
        System.out.println(s1.size());
    }
}
