class Human
{    
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


class Woman extends Human
{
    /**
     * new method
     */
    public Human giveBirth()
    {
        System.out.println("Give birth");
        return (new Human());
    }

}
public class Test
{
    public static void main(String[] args) throws Exception
    {
        Human aPerson = new Human();
        Class c1      = aPerson.getClass();
        System.out.println(c1.getName());

        Human anotherPerson = new Woman();
        Class c2      = anotherPerson.getClass();
        System.out.println(c2.getName());  

        Class c3      = Class.forName("Human");
        System.out.println(c3.getName());

        Class c4      = Woman.class;
        System.out.println(c4.getName());  
    }
}

