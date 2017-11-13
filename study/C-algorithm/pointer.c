#include <stdio.h>
int main()
{
	int n=5, *p=&n, *q;

	*q=5;

	printf("p=0x%x\n",p);
	printf("*p=%d\n",*p);

	printf("q=0x%x,*q=%d\n",q,*q);

	return 0;
}
