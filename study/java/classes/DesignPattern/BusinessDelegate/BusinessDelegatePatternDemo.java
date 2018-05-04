public class BusinessDelegatePatternDemo {
    
   public static void main(String[] args) {

      BusinessDelegate businessDelegate = new BusinessDelegate();

      Client client = new Client(businessDelegate);
      businessDelegate.setServiceType("EJB");
      client.doTask();

      businessDelegate.setServiceType("JMS");
      client.doTask();
   }
}
