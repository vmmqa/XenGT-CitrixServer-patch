public class OctalObserver implements Observer{

   public OctalObserver(){
        Subject.getInstance().attach(this);
   }

   @Override
   public void update() {
     System.out.println( "Octal String: " + Integer.toOctalString( Subject.getInstance().getState() ) ); 
   }
}
