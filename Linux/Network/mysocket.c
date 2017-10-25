#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdio.h>

int main() {
  int ret;
  struct sockaddr_in addr;
  struct sockaddr_in test_addr;
  socklen_t test_addrlen;
  addr.sin_family = AF_INET;
  addr.sin_port = htons(8080);
  // 这个 ip 地址一定要写你机器的配置的 ip 地址，不然人家怎么找到你呢？
     //addr.sin_addr.s_addr = inet_addr(INADDR_ANY);
     addr.sin_addr.s_addr = inet_addr("127.0.0.1");
  //
       int sockfd = socket(AF_INET, SOCK_STREAM, 0); 
         if (sockfd < 0) perror("socket");
           ret = bind(sockfd, (struct sockaddr*)&addr, sizeof(addr));
             if (ret < 0) perror("bind");
  
  //             // 函数 getsockname 可以用来查询当前插座绑定的套接字地址。
                 test_addrlen = sizeof(test_addr);
                   ret = getsockname(sockfd, (struct sockaddr*)&test_addr, &test_addrlen);
                     if (ret < 0) perror("getsockname");
  //
  //                     // 打印绑定的地址
                         printf("ip: %s, port: %d\n", inet_ntoa(test_addr.sin_addr), ntohs(test_addr.sin_port));
  			sleep(1000);
                           close(sockfd);
                             return 0;
                             }
