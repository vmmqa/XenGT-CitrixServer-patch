public class Test1
{
    public static void main(String[] args)
    {
        Human me        = new Human("Vamei");
        me.drinkWater(0.3);

        Human.Cup soloCup = me.new Cup(); // be careful here
    }
}

class Human
{
    /**
     * inner class
     */                                                                                                                                                             
    class Cup
    {
        public void useCup(double w)
        {
            this.water = this.water - w;
        }

        public double getWater()
        {
            return this.water;
        }

        private double water = 1.0;
    }

    /**
     * constructor
     */
    public Human(String n)
    {
        this.myCup = new Cup();
        this.name  = n;
    }

    public void drinkWater(double w)
    {
        myCup.useCup(w);
        System.out.println(myCup.getWater());
    }


    private Cup myCup;
    private String name;
}
