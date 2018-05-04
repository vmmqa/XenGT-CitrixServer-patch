public class RealCustomer extends AbstractCustomer {

   static int ID=1;
   int localid;
   public RealCustomer(String name) {
      this.name = name;
      ID++;    
      localid=ID; 
   }
   
   @Override
   public String getName() {
      return name+localid;
   }
   
   public String toString(){
        return this.getName();

    }  
   @Override
   public boolean isNil() {
      return false;
   }
}
