public class staticTest { 
        private static int a; 
        private int b; 

        static { 
                staticTest.a = 3; 
                System.out.println(a); 
                staticTest t = new staticTest(); 
                t.f(); 
                t.b = 1000; 
                System.out.println(t.b); 
        } 

        static { 
                staticTest.a = 4; 
                System.out.println(a); 
        } 

        public static void main(String[] args) { 
                // TODO 自动生成方法存根 
        } 

        static { 
                staticTest.a = 5; 
                System.out.println(a); 
        } 

        public void f() { 
                System.out.println("hhahhahah"); 
        } 
}
