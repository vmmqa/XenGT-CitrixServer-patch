public class Test1
{
    public static void main(String[] args)
    {
        MusicCup aMusic = new MusicCup();
	aMusic.addWater(10);
	aMusic.drinkWater(5);
	aMusic.play();
}
}
interface Cup {
    void addWater(int w);
    void drinkWater(int w);
}

interface MusicPlayer {
    void play();
}

class MusicCup implements MusicPlayer, Cup
{
    public void addWater(int w) 
    {
        this.water = this.water + w;
    }

    public void drinkWater(int w)
    {
        this.water = this.water - w;
    }

    public void play()
    {
        System.out.println("la...la...la, water="+water);
    }

    private int water = 0;
}
