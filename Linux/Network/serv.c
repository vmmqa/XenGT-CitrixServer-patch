#include <stdio.h>
#include <stdlib.h>
#include <arpa/inet.h>


#define ERR_EXIT(msg) do { perror(msg); exit(1); } while(0)

int main() {
  struct sockaddr_in servaddr, cliaddr;
  int sockfd, clientfd, ret;
  socklen_t cliaddrlen;

   //1. create sockaddr
     puts("1. create sockaddr");
       servaddr.sin_family = AF_INET;
         // 注意，把这个 ip 地址改成你配置的地址。
           servaddr.sin_addr.s_addr = inet_addr("127.0.0.1");
             servaddr.sin_port = htons(8080);
  
               // 2. create socket
                 puts("2. create socket");
                   sockfd = socket(AF_INET, SOCK_STREAM, 0); 
                     if (sockfd < 0) ERR_EXIT("socket");
  
                       // 3. bind sockaddr
                         puts("3. bind sockaddr");
                           ret = bind(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr));
                             if (ret < 0) ERR_EXIT("bind");
  
                               // 4. listen
                                 puts("4. listen");
                                   ret = listen(sockfd, 5); 
                                     if (ret < 0) ERR_EXIT("listen");
  
                                       // 在这里等待连接请求
                                         while(1) pause();
                                           return 0;
                                           }
