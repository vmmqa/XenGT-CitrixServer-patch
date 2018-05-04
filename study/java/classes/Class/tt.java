class A{
    
}

class B extends A {
    
}

class C extends B {
    
}


public class tt {

    /**
     * @param args
     */
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        C c = new C();
        B b = new B();
        A a = new A();
        
        B bc = new C();
        A ac = new C();
        
        System.out.println(c instanceof C);
        System.out.println(c instanceof B);
        System.out.println(c instanceof A);
        
        System.out.println();
        
        System.out.println(c.getClass().isInstance(c));
        System.out.println(c.getClass().isInstance(b));
        System.out.println(c.getClass().isInstance(a));
        
        System.out.println();
        
        System.out.println(c.getClass().isInstance(bc));
        System.out.println(c.getClass().isInstance(ac));
        
        System.out.println();
        
        System.out.println(A.class.isInstance(a));
        System.out.println(A.class.isInstance(b));
        System.out.println(A.class.isInstance(c));
        System.out.println(A.class.isInstance(ac));
        System.out.println(A.class.isInstance(bc));
        
        System.out.println();
        
        System.out.println(B.class.isInstance(a));
        System.out.println(B.class.isInstance(b));
        System.out.println(B.class.isInstance(c));
        System.out.println(B.class.isInstance(ac));
        System.out.println(B.class.isInstance(bc));
        
        
    }

}

