 //cli.c
 #include <stdio.h>
 #include <stdlib.h>
 #include <arpa/inet.h>

 #define ERR_EXIT(msg) do { perror(msg); exit(1); } while(0)

 int main(int argc, char **argv) {
   int sockfd, ret;
     struct sockaddr_in servaddr;
       struct sockaddr_in cliaddr;
         socklen_t cliaddrlen;
        
          //  for(int i=0;i<argc;i++){
          //      printf("arg%d=%s\n",i,argv[i]);}
           // 目标网络进程的套接字地址
             servaddr.sin_family = AF_INET;
               //servaddr.sin_addr.s_addr = inet_addr("127.0.0.1");
               //  servaddr.sin_port = htons(atoi("8080"));
               servaddr.sin_addr.s_addr = inet_addr(argv[1]);
                 servaddr.sin_port = htons(atoi(argv[2]));

                   // 创建 socket
                     sockfd = socket(AF_INET, SOCK_STREAM, 0); 
                       if (sockfd < 0) ERR_EXIT("socket");

                         // 发起连接
                           ret = connect(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr));
                             if (ret < 0) ERR_EXIT("connect");

                               // 查看 connect 为我们将 socket 绑定到了哪个套接字地址上。
                                 cliaddrlen = sizeof(cliaddr);
                                   ret = getsockname(sockfd, (struct sockaddr*)&cliaddr, &cliaddrlen);
                                     if (ret < 0) ERR_EXIT("getsockaddr");

                                       printf("cliaddr: %s:%d\n", inet_ntoa(cliaddr.sin_addr), ntohs(cliaddr.sin_port));

                                         return 0;
                                         }
