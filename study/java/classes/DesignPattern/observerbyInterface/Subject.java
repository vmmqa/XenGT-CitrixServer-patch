import java.util.ArrayList;
import java.util.List;

public class Subject {
 
   private static Subject instance = new Subject();

   private Subject() {}   
   private List<Observer> observers = new ArrayList<Observer>();
   private int state;

   public static Subject getInstance(){
    return instance;
    }


   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
      notifyAllObservers();
   }

   public void attach(Observer observer){
      observers.add(observer);      
   }

   public void notifyAllObservers(){
      for (Observer observer : observers) {
         observer.update();
      }
   }    
}
