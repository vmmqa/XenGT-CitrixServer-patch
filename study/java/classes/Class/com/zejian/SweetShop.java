package com.zejian;

class Candy {
  static {   System.out.println("Loading Candy"); }
}

class Gum {
  static {   System.out.println("Loading Gum"); }
}

class Cookie {
  static {   System.out.println("Loading Cookie"); }
}

public class SweetShop {
  public static void print(Object obj) {
    System.out.println(obj);
  }
  public static void main(String[] args) {  
    print("inside main");
    new Candy();
    print("After creating Candy");
    try{
      //通过Class.forName获取Gum类的Class对象
      Class clazz=Class.forName("com.zejian.Gum");
      System.out.println("forName=clazz:"+clazz.getName());
    }catch (ClassNotFoundException e){
      e.printStackTrace();
    }

    //通过实例对象获取Gum的Class对象
    Gum gum = new Gum();
    Class clazz2=gum.getClass();
    System.out.println("new=clazz2:"+clazz2.getName());

    new Cookie();
    print("After creating Cookie");
  }
}
