public class Test
{
    public static void main(String[] args)
    {
        NewThread thread1 = new NewThread();
        NewThread thread2 = new NewThread();
        thread1.start(); // start thread1
        thread2.start(); // start thread2
    }
}

/**
 * create new thread by inheriting Thread
 */
class NewThread extends Thread {

    private static int threadID = 0; // shared by all

    /**
     * constructor
     */
    public NewThread() {
        super("ID:" + (++threadID));
    }

    /**
     * convert object to string
     */
 //   public String toString() {
 //       return super.getName();
 //   }

    /**
     * what does the thread do?
     */
    public void run() {
        System.out.println(this);
    }
}
