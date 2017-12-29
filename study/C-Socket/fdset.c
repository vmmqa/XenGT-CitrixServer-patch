#include <unistd.h>
#include <sys/select.h>

// 打印前 16 个小隔间
void printset(const fd_set *st) {
  int i;
    for (i = 0; i < 16; ++i) {
        if (FD_ISSET(i, st))
              putchar('1');
        else
                putchar('0');
    }
    puts("");
}

int main() {
    int i;
    fd_set st; 
    // 未初始化的集合
    puts("uninitial");
    printset(&st);

    // 清空集合
    puts("zeros");
    FD_ZERO(&st);
    printset(&st);

    // 将奇数描述符保存到集合
    puts("set odd");
    for (i = 0; i < 16; ++i) {
        if (i%2) {
            FD_SET(i, &st);
            }   
    }
    printset(&st);

    // 将 3 号描述符删除
    puts("clear 3");
    FD_CLR(3, &st);
    printset(&st);

    // 清空集合
    puts("zeros");
    FD_ZERO(&st);
    printset(&st);

    return 0;
}
