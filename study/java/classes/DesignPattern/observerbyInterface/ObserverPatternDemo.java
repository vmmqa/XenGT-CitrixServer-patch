public class ObserverPatternDemo {
   public static void main(String[] args) {
      Subject subject = Subject.getInstance();

      new HexaObserver();
      new OctalObserver();
      new BinaryObserver();

      System.out.println("First state change: 15"); 
      subject.setState(15);
      System.out.println("Second state change: 10");    
      subject.setState(10);
   }
}
