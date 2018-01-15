public class Test3
{
    public static void main(String[] args)
    {
        Human.Mongolian him = new Human.Mongolian();
        him.Shout();
    }
}

class Human
{
    /**
     * nested class
     */
    static class Mongolian
    {
        public void Shout()
        {
            System.out.println("Oh...Ho...");
        }
    }
}
