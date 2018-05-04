//动态代理的优缺点
//优点：动态代理和静态代理相比，最大的好处就是借口中声明的所有方法都被转移到调用处理器一个集中的方法（invoke）中处理。这样在借口方法数量比较多的时候，我们可以进行灵活处理，而不需要像静态代理那样，每一个方法进行中转。

//缺点：仅支持interface代理。由于java的集成机制注定了这些动态代理们无法实现对class的动态代理，因为java不能实现多继承。

import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.util.ArrayList;  
import java.util.List;  

interface UserManager {
    public void addUser(String userId,String userName);
    
    public void delUser(String userId);
}
class UserManagerImpl implements UserManager {
    public void addUser(String userId, String userName) {
        try {
            System.out.println("UserManagerImpl.addUser() userId-->>"+ userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    public void delUser(String userId) {
            System.out.println("UserManagerImpl.delUser() userId-->>"+ userId);
    }
}


class LogHandler implements InvocationHandler {
    private Object targetObject;
    
    public Object newProxyInstanceObject(Object targetObject) {
        this.targetObject=targetObject;
        return  Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), 
                targetObject.getClass().getInterfaces(), this); 
    }
    
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("start-->>"+method.getName());
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        
        Object retObject=null;
        try{
            //调用目标方法
            method.invoke(targetObject, args);
            System.out.println("success-->>"+method.getName());
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("error-->>"+method.getName());
            throw e;
        }
        return null;
    }
}
class UserManagerImplProxy implements UserManager {
    private UserManager userManager;
    
    public UserManagerImplProxy(UserManager iuserManager) {
            userManager=iuserManager;}

    public void addUser(String userId, String userName){
       try {
            System.out.println("start-->>addUser() userId-->>" + userId);
            userManager.addUser(userId, userName);
            System.out.println("success-->>addUser()");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error-->>addUser()");
        }
    }
    
    public void delUser(String userId) {
        try {
            System.out.println("start-->>addUser() userId-->>" + userId);
            userManager.delUser(userId);
            System.out.println("success-->>addUser()");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("error-->>addUser()");
        }
    }
}
public class dynamicClient {
    public static void main(String[] args) {
        
        LogHandler logHandler=new LogHandler();
        UserManager userManager=(UserManager)logHandler.newProxyInstanceObject(new UserManagerImpl());
        userManager.addUser("0001", "张三");
        userManager.delUser("0001");
      //  String name=userManager.findUser("0001");
      //  System.out.println("Client.main() --- " +name);
    }
}

