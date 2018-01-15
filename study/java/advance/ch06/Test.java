public class Test
{
    public static void main(String[] args)
    {
        int[] aFrom = {1, 2, 3, 7, 9}; // array size 5
        int[] aTo  = new int[3];
        System.arraycopy(aFrom, 1, aTo, 0, 3);
        System.out.println(aTo[1]);
    }
}
