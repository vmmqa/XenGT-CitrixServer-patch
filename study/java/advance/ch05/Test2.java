public class Test2
{
    public static void main(String[] args)
    {
        Thread thread1 = new Thread(new NewThread(), "first");
        Thread thread2 = new Thread(new NewThread(), "second");
        thread1.start(); // start thread1
        thread2.start(); // start thread2
    }
}

/**
 * create new thread by implementing Runnable
 */
class NewThread implements Runnable {
    /**
     * convert object to string
     */
    public String toString() {
        return Thread.currentThread().getName();
    }

    /**
     * what does the thread do?
     */
    public void run() {
        System.out.println(this);
    }
}
