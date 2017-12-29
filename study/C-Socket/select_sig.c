// select_sig.c

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/select.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <signal.h>

#define PERR(msg) do { perror(msg); exit(1); } while(0);

void handler(int sig) {
  if (sig == SIGALRM)
      puts("Hello SIGALRM");
      }

int process(char* prompt, int fd) {
  int n;
    char buf[64];
    char line[64];
    n = read(fd, buf, 64);
    if (n < 0) {
    // error
        PERR("read");
    }
    else if (n == 0) {
    // peer close
    sprintf(line, "%s closed\n", prompt);
    puts(line);
    return 0;
    }
    else if (n > 0) {
    buf[n] = 0;
    sprintf(line, "%s say: %s", prompt, buf);
    puts(line);
    }
    return n;
}

int main () {
  int n, res, fd0, maxfd;
    char buf[64];
      struct sigaction sa; 
        fd_set st; 

          // 打印 pid
  printf("pid = %d\n", getpid());

    // 安装信号处理函数
    sa.sa_handler = handler;
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = 0;
    sigaction(SIGALRM, &sa, NULL);

    // 为了简化程序，这里只管理一个描述符
     FD_ZERO(&st);
     fd0 = STDIN_FILENO;
      FD_SET(fd0, &st);

      maxfd = fd0 + 1;

    while(1) {
       fd_set tmpset = st;
       res = select(maxfd, &tmpset, NULL, NULL, NULL);

       if (res < 0) {
          // 如果被信号打断的，不让程序退出，直接 continue
          if (errno == EINTR) {
                perror("select");
                continue;
           }
             // 其它情况的错误，直接让程序退出
                PERR("select");
        }
        else if (res == 0) {
        // timeout
        continue;
        }

        if (FD_ISSET(fd0, &tmpset)) {
            n = process("fd0", fd0);
            if (n == 0) FD_CLR(fd0, &st);
        }
}
}
