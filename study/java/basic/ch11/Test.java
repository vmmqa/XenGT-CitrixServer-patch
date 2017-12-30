class Human
{

   public Human(int h){
	this.height=h;
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

    /**
     * breath
     */
    public void breath()
    {
        System.out.println("hu...hu...");
    }

    private int height; 
}
class Woman extends Human
{
	public Woman(int h){
		super(h);
		System.out.println("hello, Pandora!");
	}
    /**
     * new method
     */
    public Human giveBirth()
    {
        System.out.println("Give birth");
        return (new Human(20));
    }
}

/**public class Test
{
    public static void main(String[] args)
    {
        Woman aWoman = new Woman(10);
        aWoman.growHeight(120);
        System.out.println(aWoman.getHeight());                                             
    }
}*/
public class Test
{
    public static void main(String[] args)
        {
             Human aPerson = new Human(160);
             Human dummyPerson = aPerson;
             System.out.println(dummyPerson.getHeight());
             aPerson.growHeight(20);
             System.out.println(dummyPerson.getHeight());
        }
}
