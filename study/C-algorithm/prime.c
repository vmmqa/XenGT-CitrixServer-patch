#include <stdio.h>

int main(int argc, char **argv){

	int value;
	scanf("%d",&value);
	printf("the value=%d",value);

	if(value==2 || value ==3)
		return 0;
	for(int i=2;i*i<=value;i++){
		if(value%i==0){
			printf("it is prime,i=%d\n",i);
			return 0;}
		
	}
	
	return -1;
	

}
