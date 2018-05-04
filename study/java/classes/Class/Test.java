public class Test
{
    public static void main(String[] args) throws Exception
    {
        Human aPerson = new Human(160);
        Class c1=Class.forName("Human");
        System.out.println(aPerson.toString());
        System.out.println(c1.toString());
    }

}

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

     /**
      * encapsulated, for internal use
      */
    private void breath()
    {
        System.out.println("hu...hu...");
    }


   /**
    * call breath()
    */
    public void repeatBreath(int rep)
    {
        int i;
        for(i = 0; i < rep; i++) {
            this.breath();
        }
    }

    private int height; // encapsulated, for internal use
}
