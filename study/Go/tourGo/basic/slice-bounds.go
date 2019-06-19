package main

import "fmt"

func main() {
    s := []int{2, 3, 5, 7, 11, 13}

    a := s[1:4]
    fmt.Println(a)

    b := s[:2]
    fmt.Println(b)

    c := s[1:]
    fmt.Println(c)
}

