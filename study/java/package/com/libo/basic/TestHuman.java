
class Human
{
    /**
     * constructor
     */
    public Human(int h)
    {
        this.height = h;
        System.out.println("I'm born");
    }

    /**
     * accessor
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * mutator
     */
    public void growHeight(int h)
    {
        this.height = this.height + h;
    }

    private int height;
}

public class TestHuman{
    public static void main(String[] args) throws Exception
    {
        Human test=new Human(1);
        
        System.out.println("Hello World!");

    }
}
