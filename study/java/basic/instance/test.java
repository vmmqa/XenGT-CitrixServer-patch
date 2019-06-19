public class test {
    public static void main(String arg[]) {
        ClassA a = new ClassA();
        ClassB b = new ClassB();
        ClassC c = new ClassC();
        sayClass(a);
        sayClass(b);
        sayClass(c);
        System.out.println("---------------------------------");
        equalClass(a);
        equalClass(b);
        equalClass(c);
    }
    public static void sayClass(Object o){
        if(o instanceof ClassA){
            System.out.println("ClassA");
        }else if(o instanceof ClassB){
            System.out.println("ClassB");
        }
    }
    
    public static void equalClass(Object o){
        if(o.getClass().equals(ClassA.class)){
            System.out.println(o.getClass().getName());
        }else if(o.getClass().equals(ClassB.class)){
            System.out.println(o.getClass().getName());
        }else if(o.getClass().equals(ClassC.class)){
            System.out.println(o.getClass().getName());
        }
    }
}
class ClassA{
    ClassA(){};
}
class ClassB{
    ClassB(){};
}

class ClassC extends ClassA{
    ClassC(){};
}
