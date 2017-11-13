package main
import "fmt"
 
func max(a int, b int) int { //注意参数和返回值是怎么声明的
 
    if a > b {
        return a
    }
    return b
}
 
func main(){
    fmt.Println(max(4, 5))
}
