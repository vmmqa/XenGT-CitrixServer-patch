#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#define ERR_EXIT(msg) do { perror(msg); exit(1); } while(0)

int main() {
  int sockfd, ret, n;
    char buf[64];
      struct sockaddr_in servaddr;
        struct sockaddr_in cliaddr;
          socklen_t cliaddrlen;

            servaddr.sin_family = AF_INET;
              servaddr.sin_addr.s_addr = inet_addr("127.0.0.1");
                servaddr.sin_port = htons(8080);

                  sockfd = socket(AF_INET, SOCK_STREAM, 0); 
                    if (sockfd < 0) ERR_EXIT("socket");

                      ret = connect(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr));
                        if (ret < 0) ERR_EXIT("connect");

                          cliaddrlen = sizeof(cliaddr);
                            ret = getsockname(sockfd, (struct sockaddr*)&cliaddr, &cliaddrlen);
                              if (ret < 0) ERR_EXIT("getsockaddr");

                                printf("cliaddr: %s:%d\n", inet_ntoa(cliaddr.sin_addr), ntohs(cliaddr.sin_port));

                                  while(1) {
                                      // 从标准输入读取数据
                                          scanf("%s", buf);
                                              // 如果收到的第一个字母是 q 就退出循环，关闭连接。
                                                  if (buf[0] == 'q') break;
                                                      // 将数据发送给服务器
                                                          write(sockfd, buf, strlen(buf));
                                                              // 从服务器读取处理完的数据
                                                                  n = read(sockfd, buf, 63);
                                                                      buf[n] = 0;
                                                                          // 将结果打印在屏幕上
                                                                              puts(buf);
                                                                                }

                                                                                  close(sockfd);
                                                                                    return 0;
                                                                                    }
