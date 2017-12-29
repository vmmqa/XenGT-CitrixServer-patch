public class Test3
{
    public static void main(String[] args)
    {
        Human aPerson = new Human();
        System.out.println(aPerson.getHeight());
        aPerson.growHeight(10);
        System.out.println(aPerson.getHeight());
    }

}

class Human
{
/**
     * accessor
     */
    int getHeight()
    {
        return this.height;
    }

    /**
     * pass argument
     */
    void growHeight(int h)
    {
        this.height = this.height + h;
    }

    int height;
}
