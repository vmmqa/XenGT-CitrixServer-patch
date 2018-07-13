class Test1 { 
public void f1() { 
    System.out.println("f1"); 
} 
//无法被子类覆盖的方法 
public final void f2() { 
    System.out.println("f2"); 
} 
public void f3() { 
    System.out.println("f3"); 
} 
private void f4() { 
    System.out.println("f4"); 
} 
} 

public class finalTest extends Test1 { 
    
public void f1(){     
    System.out.println("Test1父类方法f1被覆盖!"); 
} 
public static void main(String[] args) { 
    finalTest t=new finalTest(); 
    t.f1();    
    t.f2(); //调用从父类继承过来的final方法 
    t.f3(); //调用从父类继承过来的方法 
    //t.f4(); //调用失败，无法从父类继承获得 
} 
}
