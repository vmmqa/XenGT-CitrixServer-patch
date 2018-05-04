//static proxy
//静态代理的优缺点
//优点：业务类只需要关注业务逻辑本身，保证了业务类的重用行。这是代理的共同优点。
//缺点：代理对象的一个接口只服务于一种类型的对象，如果要代理的方法很多，势必要为每一种方法都进行代理，静态代理在程序规模稍大时就无法胜任了。如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度

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


public class Client {
    public static void main(String[] args) {
        UserManager userManager=new UserManagerImplProxy(new UserManagerImpl());
        userManager.addUser("0001", "张三");
    }
}

