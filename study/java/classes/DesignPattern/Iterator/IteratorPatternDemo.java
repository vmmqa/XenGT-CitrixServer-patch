public class IteratorPatternDemo {
    
   public static void main(String[] args) {
      NameRepository namesRepository = new NameRepository();

        for (int i=0;i<0;){
        System.out.println("i="+i);
    }
      for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
         String name = (String)iter.next();
         System.out.println("Name : " + name);
      }     
   }
}
