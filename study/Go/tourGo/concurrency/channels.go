package main

import "fmt"

func sum(s []int, c chan int) {
    sum := 0
    for _, v := range s {
        sum += v
    }
    fmt.Println("sum=",sum)
    c <- sum // 将和送入 c
}

func main() {
    s := []int{7, 2, 8, -9, 4, 0}

    c := make(chan int)
    go sum(s[:len(s)/2], c)
    go sum(s[len(s)/2:], c)
    fmt.Println("before receive")
    x, y := <-c, <-c // 从 c 中接收
    fmt.Println("after receive")
    fmt.Println(x, y, x+y)
}
