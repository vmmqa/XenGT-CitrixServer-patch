public class HexaObserver implements Observer{

   public HexaObserver(){
       Subject.getInstance().attach(this);
   }

   @Override
   public void update() {
      System.out.println( "Hex String: " + Integer.toHexString( Subject.getInstance().getState() ).toUpperCase() ); 
   }
}
