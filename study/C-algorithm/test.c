#include <stdio.h>
int partition(int low, int high)
{
	printf("low=%d,high=%d\n",low,high);

	high--,low++;
	
	printf("low=%d,high=%d"
			"asdf"
			"\n",low,high);
	return 0;
}


void main()
{
	partition(1,5);
	return 0;
}
