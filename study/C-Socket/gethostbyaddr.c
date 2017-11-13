//#include "../common.h"
#include <stdio.h>
//#include <
#include <netdb.h>
#include <arpa/inet.h>

void
print_type(int type)
{
    printf(" type ");
    switch (type) {
    case SOCK_STREAM:
        printf("stream\n");
        break;
    case SOCK_DGRAM:
        printf("datagram\n");
        break;
    case SOCK_SEQPACKET:
        printf("seqpacket\n");
        break;
    case SOCK_RAW:
        printf("raw\n");
        break;
    default:
        printf("unknown (%d)", type);
    }   

}
int main(int argc, char **argv)
{

    struct hostent *host;
    host=gethostbyaddr(argv[1],4,AF_INET);
    struct in_addr addr;
    char **pp;
    printf("h_name=%s\n",host->h_name);
    printf("h_addrtype=0x%x\n",host->h_addrtype);
    print_type(host->h_addrtype);
    printf("h_length=%d\n",host->h_length);

    for (pp = host->h_addr_list; *pp != NULL; pp++){
        addr.s_addr=((struct in_addr *) *pp)->s_addr;
        printf("address:%s\n",inet_ntoa(addr));

    }

}
