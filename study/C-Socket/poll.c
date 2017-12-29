// poll.c

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/select.h>
#include <poll.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <signal.h>

#define PERR(msg) do { perror(msg); exit(1); } while(0);

void handler(int sig) {
  if (sig == SIGINT)
      puts("Hello SIGINT");
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
    int i,n, res;
    char buf[64];
    struct pollfd fds[4];

    if (SIG_ERR == signal(SIGINT, handler)) {
        PERR("signal");
          }

    int fd0 = STDIN_FILENO;
    int fd1 = open("a.fifo", O_RDONLY);
    printf("open pipe: fd = %d\n", fd1);
    int fd2 = open("b.fifo", O_RDONLY);
    printf("open pipe: fd = %d\n", fd2);
    int fd3 = 100;

    fds[0].fd = fd0;
    fds[1].fd = fd1;
    fds[2].fd = fd2;
    fds[3].fd = fd3;

    for (i = 0; i < 4; ++i) {
        fds[i].events = POLL_IN;
    }

  while(1) {
      res = poll(fds, 4, -1);

          if (res < 0) {
                // error
            if (errno == EINTR) {
                perror("poll");
                continue;
            }
                PERR("poll");
            }
            else if (res == 0) {
            // timeout
            continue;
            }

            for (i = 0; i < 4; ++i) {
                if (fds[i].revents & POLLIN) {
                  sprintf(buf, "fd%d", i);
                    process(buf, fds[i].fd);
                    if (n == 0) fds[i].fd = -1;
                }   
                if (fds[i].revents & POLLERR) {
                    printf("fd%d Error\n", i);
                    fds[i].fd = -1;
                }
                if (fds[i].revents & POLLHUP) {
                    printf("fd%d Hang up\n", i);
                fds[i].fd = -1;}
                if (fds[i].revents & POLLNVAL) {
                   printf("fd%d Invalid request\n", i);
                fds[i].fd = -1;
                }
           }
        }
}
