package com.libo.basic;

public class Human
{
    /**
     * constructor
     */
    public Human(int h)
    {
        this.height = h;
        System.out.println("I'm born");
    }

    /**
     * accessor
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * mutator
     */
    public void growHeight(int h)
    {
        this.height = this.height + h;
    }

    private int height;
}
