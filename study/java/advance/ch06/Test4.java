import java.util.*;

public class Test4
{
    public static void main(String[] args)
    {
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(4);
        l1.add(5);
        l1.add(2);
        Iterator i = l1.iterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
