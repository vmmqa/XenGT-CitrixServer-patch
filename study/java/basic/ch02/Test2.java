public class Test2
{
    public static void main(String[] args)
    {
        Human aPerson = new Human();
        aPerson.breath();
        System.out.println(aPerson.height);
    }

}

class Human
{
    void breath()
    {
       System.out.println("hu...hu...");
    }

    int height;
}
