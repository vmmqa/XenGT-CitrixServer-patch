package com.jg.zhang;

public class Person {

    int age;

    int getAge(){
        return age;
    }
    public Person(int age){
        this.age=age;

    }

    public static void main(String[] args){

        Person p1 = new Person();
        System.out.println("age="+p1.getAge());
    }
}




