package test;

public class SubjectProxy implements Subject
{
  Subject subimpl = new RealSubject();
  public void doSomething()
  {
     subimpl.doSomething();
  }
}

