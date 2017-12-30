public class Sum
{
    public static void main(String[] args)
    {
        System.out.println("the result of 1+2+...+999:");
	int sum=0;
	for (int i=1;i<1000;i++)
		sum+=i;
        System.out.println(sum);  // print an integer
    }
}
