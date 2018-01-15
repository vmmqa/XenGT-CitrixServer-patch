class Test2
{
    public static void main(String[] args)
    {
        Human me        = new Human("Vamei");
        Human him       = new Human("Jerry");

        Human.Cup myFirstCup  = me.new Cup();
        Human.Cup mySecondCup = me.new Cup();
        Human.Cup hisCup      = him.new Cup();
        System.out.println(myFirstCup.whosCup());
        System.out.println(mySecondCup.whosCup());
        System.out.println(hisCup.whosCup());
    }
}

class Human
{
    /**
     * inner class
     */
    class Cup
    {
        public String whosCup()
        {
            return name;  // access outer field
        }
    }

    /**
     * constructor
     */
    public Human(String n)
    {
        this.name = n;
    }


    public void changeName(String n)
    {
        this.name = n;
    }

    private String name;
}
