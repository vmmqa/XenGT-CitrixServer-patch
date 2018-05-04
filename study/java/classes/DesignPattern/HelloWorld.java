 class Singleton{
    private Singleton() {
        System.out.println("single Done");
    }

    private static Singleton Instance = null;
    
    public static Singleton GetInstance(){
        if(Instance == null){
            Instance = new Singleton();
    }
    return Instance;
    
    }


}


public class HelloWorld
{
    public static void main(String[] args)
	    {

                Singleton test1=Singleton.GetInstance();
                Singleton test2=Singleton.GetInstance();
		        System.out.println("Hello World!");
		}
}
