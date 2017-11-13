package main
 
import "os"
import "strings"
 
 
func main() {
    os.Setenv("WEB", "https://coolshell.cn") //设置环境变量
    println(os.Getenv("WEB")) //读出来
 
    for _, env := range os.Environ() { //穷举环境变量
        e := strings.Split(env, "=")
        println(e[0], "=", e[1])
    }
}
