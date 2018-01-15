public class Test
{
    public static void main(String[] args)
    {
        Human me        = new Human("Vamei");
        me.drinkWater(0.3);
    }
}

class Human
{
    /**
     * inner class
     */
    private class Cup
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
