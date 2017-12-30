public class Test2
{
    public static void main(String[] args)
    { 
        Cup aCup;
	System.out.println("before new");
        BrokenCup aBrokenCup = new BrokenCup();
	System.out.println("after new");
        aCup = aBrokenCup; // upcast
        aCup.addWater(10); // method binding
    }
}

class Cup 
{
    public Cup(){
	System.out.println("constrcutor Cup");
	}
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
    public BrokenCup(){
		System.out.println("constructor BrokenCpu");
	}
    public void addWater(int w) 
    {
        System.out.println("shit, broken cup");
    }

    public void drinkWater(int w)
    {
        System.out.println("om...num..., no water inside");
    }
}
