package main

import (
    "fmt"
//    "math"
)

func Sqrt(x float64) float64 {
    var z float64 = 1.0
    for i := 0; i< 10; i++ {
        z -= (z*z - x) / (2*z)
        fmt.Println(z)
    }
    return z
//    return math.Sqrt(x);
}

func main() {
    fmt.Println(Sqrt(2))
}
