public class BinaryObserver implements Observer{

   public BinaryObserver(){
      Subject.getInstance().attach(this);
   }

   @Override
   public void update() {
      System.out.println( "Binary String: " + Integer.toBinaryString( Subject.getInstance().getState() ) ); 
   }
}
