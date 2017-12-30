public class Test4
{
    public static void main(String[] args)
    {
        Human aPerson = new Human();
        aPerson.repeatBreath(10);
    }

}

class Human
{
    void breath()
    {
        System.out.println("hu...hu...");
    }


   /**
    * call breath()
    */
    void repeatBreath(int rep)
    {
        int i;
        for(i = 0; i < rep; i++) {
            this.breath();
        }
    }
    int height;
}
