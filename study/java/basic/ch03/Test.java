public class Test
{
    public static void main(String[] args)
    {
        Human aPerson = new Human(160);
        System.out.println(aPerson.getHeight());
    }

}

class Human
{
    /**
     * constructor
     */
    Human(int h)
    {
        this.height = h;
        System.out.println("I'm born");
    }

    /**
     * accessor
     */
    int getHeight()
    {
        return this.height;
    }

    int height;
}
