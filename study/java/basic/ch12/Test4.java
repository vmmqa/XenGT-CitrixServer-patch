class Cup 
{
    public void addWater(int w) 
    {
        this.water = this.water + w;
    }

    public void drinkWater(int w)
    {
        this.water = this.water - w;
    }

    private int water = 0;
}

class BrokenCup extends Cup
{
    public void addWater(int w) 
    {
        System.out.println("shit, broken cup");
    }

    public void drinkWater(int w)
    {
        System.out.println("om...num..., no water inside");
    }
}

public class Test4
{
    public static void main(String[] args)
    {
        Human guest = new Human();
        BrokenCup hisCup  = new BrokenCup();
        guest.drink(hisCup, 10);
    }
}

class Human 
{
    void drink(Cup aCup, int w)
    {
        aCup.drinkWater(w);
    }
}
