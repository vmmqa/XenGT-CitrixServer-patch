package test;

public class TestProxy 
{
   public static void main(String args[])
   {
       Subject sub = new SubjectProxy();
       sub.doSomething();
   }
}

