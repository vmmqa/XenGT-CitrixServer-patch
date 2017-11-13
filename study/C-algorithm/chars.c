#include <stdio.h>
#include <stdlib.h>

int main(){
	char ts[]="abcdabcdabcabe";
	//char ps[]="abcabe";
	char ps[]="abcabed";

	int num_ts=sizeof(ts)/sizeof(ts[0])-1;
	int num_ps=sizeof(ps)/sizeof(ps[0])-1;
	
	printf("the result is %d\n", bruteForce(ts,ps,num_ts,num_ps));

	int *next=(int *)malloc(num_ps*sizeof(int));
	//int next[6];
	getnext(next,ps,num_ps);
	printf("the result is %d\n", KMP(next, ts,ps,num_ts,num_ps));
	free(next);
	next=NULL;
	return 0;
}

int bruteForce(char *ts,char *ps, int num_ts, int num_ps){
	printf("size ts=%d,size ps=%d\n",num_ts,num_ps);

	int i=0, j=0;
	while(i<num_ts && j<num_ps){
		if(ts[i]==ps[j]){
			i++;j++;
		}else{
		i=i-j+1;
		j=0;
		}
	}

	if(j==num_ps){
		printf(" func %s,found it, the pos is =%d\n",__func__,i-j);
		return i-j;
	}else{
		printf(" func %s,NOT found \n",__func__);
		return -1;
	}


}

int getnext(int *next, char *ps, int num_ps){
	next[0]=-1;

	int k=-1;
	int i=0;
	while(i<num_ps-1){
		if(k==-1 || ps[i]==ps[k])
		{	next[++i]=++k;
		}
		else
			k=next[k];
	}


	for(int i=0;i<num_ps;i++)
		printf("%d ",next[i]);
	
	printf("\n");
	return 0;
	
}
int KMP(int *next, char *ts,char *ps, int num_ts, int num_ps){
	printf("size ts=%d,size ps=%d\n",num_ts,num_ps);

	int i=0, j=0;
	while(i<num_ts && j<num_ps){
		if(j==-1 || ts[i]==ps[j]){
			i++;j++;
		}else{
			j=next[j];
		}
	}

	if(j==num_ps){
		printf(" func %s,found it, the pos is =%d\n",__func__,i-j);
		return i-j;
	}else{
		printf(" func %s,NOT found \n",__func__);
		return -1;
	}


}
