#include <stdio.h>
#include <signal.h>
char **args;
void exithandle(int sig)
{
       printf("%s : sighup received ",args[1]);
}
int main(int argc,char **argv)
{
       args = argv;
       signal(SIGHUP,exithandle);
       pause();
       return 0;
}
