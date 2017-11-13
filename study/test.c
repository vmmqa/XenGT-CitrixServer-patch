#include <stdio.h>
#include <linux/sched.h>

void main()
{
	printf("size int=%d\n",sizeof(int));
	printf("size task_struct=%d\n",sizeof(struct task_struct));
	return;

}
