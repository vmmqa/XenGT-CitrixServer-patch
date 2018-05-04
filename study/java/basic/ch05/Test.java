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
        System.out.println("la...la...la");
    }

    private int water = 0;
}

public class Test
{
    public static void main(String[] args)
    {
        MusicCup aMusic = new MusicCup();
        System.out.println("add water");
        aMusic.addWater(10);
        aMusic.play();
        System.out.println("drink water");
        aMusic.addWater(9);
        aMusic.play();
    }
}
