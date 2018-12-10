#include <stdio.h>
#include <stdlib.h>

static int swap_count=0;
static void print(int *p, unsigned int size)
{
	for(int i=0; i<size;i++)
		printf("i=%d,value=%d\n",i,p[i]);

	printf("total count=%d\n",swap_count);
	return;
}

static void swap(int *p, int a, int b)
{
	if(a==b)
		return;
	p[a]=p[a]^p[b];
	p[b]=p[a]^p[b];
	p[a]=p[a]^p[b];
	swap_count++;
	return;
}

static void insert_sort(int *p,  int size)
{
	printf("leverage func %s, size=%d\n",__func__,size);
	for(int i=0;i<size-1;i++){
		for(int j=i+1;j>0;j--){
			if(p[j]<p[j-1])
				swap(p,j,j-1);
			else
				break;
		}		
	}
	return ;
}

static void bubble_sort(int *p,  int size)
{
	printf("leverage func %s, size=%d\n",__func__,size);
	for(int i=0;i<size;i++){
		int sign=0;
		for( int j=size-1;j>i;j--) 
			if(p[j]<p[j-1]){
				swap(p,j,j-1);
				sign=1;
			}
		if(sign==0){
			printf("return early\n");
			break;}
	}


	return;
}

static void select_sort(int *p, int size)
{
	printf("leverage func %s, size=%d\n",__func__,size);
	for(int i=0;i<size-1;i++){
		int min=i;
		for(int j=i+1;j<size;j++)
			if(p[j]<p[min])
				min=j;
		swap(p,i,min);
	}
	return;
}

static void shell_sort(int *p, int size){
	printf("leverage func %s, size=%d\n",__func__,size);
	int step=1;
	while(step<size/2) step=step*2;
	printf("step=%d\n",step);

	while(step>=1){
		for(int l=0;l<step;l++){
			for(int i=l;i<size-1;i+=step){
				int j=i;
				while(j<=size-step) j+=step;
		
				for(;j>i;j-=step){
					if(p[j]<p[j-step])
						swap(p,j,j-step);
					else
						continue;
				}
			}
		}		
	step=step>>1;
	}

	return ;
}


static void mergecpy(int *a, int left, int middle, int right)
{
	printf("leverage func %s, left=%d,middle=%d,right=%d\n",__func__,left,middle,right);
	if(left>=right)
		return;

	int *p1=(int*)malloc((right-left+1)*sizeof(int));
	//int p1[100];
	int i=left;
	int j=middle+1;
	int k=0;
	while((i<=middle) && (j<=right)){
		while((a[i]<=a[j]) && (i<=middle))
			p1[k++]=a[i++];

		while((a[j]<a[i]) && (j<=right))
			p1[k++]=a[j++];

	}

	while(i<=middle)
		p1[k++]=a[i++];	

	while(j<=right)
		p1[k++]=a[j++];

	for(int i=0,j=left;i<right-left+1;i++,j++)
		a[j]=p1[i];

	free(p1);
	p1=NULL;
	return;

}

static void mergesort(int *a, int left,int right)
{
	printf("leverage func %s, left=%d,right=%d\n",__func__,left,right);
    if(left<right)
    {
        int middle = (left+right)/2;
        mergesort(a,left,middle);
        mergesort(a,middle+1,right);//分成二部分

        mergecpy(a,left,middle,right);//合并在一起。
    }
	return;
}

static void mergecpy2(int *p, int left, int middle, int right){
    printf("leverage func %s, left=%d, middle=%d, right=%d\n",__func__,left,middle, right);
    int *p1=(int*)malloc((right-left+1)*sizeof(int));
    
    int a=left;
    int b=middle+1;
    int i=0;
    while((a<=middle) && (b<=right)){
        if(p[a] <= p[b]){
            p1[i++]=p[a++];
        }else{
            p1[i++]=p[b++];
        }
        
    }

    int start=a;
    int end=middle;
    if(a>middle){
        start =b;
        end=right;
    }

    while(start<=end){
        p1[i++]=p[start++];
    }

    for(i=0;i<right-left+1;i++){
        p[left+i]=p1[i];
    }
    free(p1);
}

static void mergesort2(int *p, int left, int right){
	printf("leverage func %s\n",__func__);
    if(left>=right) 
        return;

    int middle = left + (right-left)/2;
    mergesort2(p, left, middle);
    mergesort2(p,middle+1,right);

    mergecpy2(p,left,middle,right);
    return;
}




static void merge_sort(int *p, int size){
	printf("leverage func %s, size=%d\n",__func__,size);
	mergesort2(p,0,size-1);
	return ;
}

static void quicksort2(int *a, int low,int high)
{
	printf("leverage func %s, left=%d,right=%d\n",__func__,low,high);
	int i=low,j=high;
    if(low<high)
    {
        int middle = low;
	//divide it into two part. [left,middle-1]<a[middle]; [middle+1,right]>=a[middle];
//	int i=left,j=right;
	int temp=a[low];
	while(low<high){
		while(a[high]>=temp && (low<high))
			high--;
		a[low]=a[high];
		while((a[low]<temp) && (low<high))
			low++;
		a[high]=a[low];
	}
	a[low]=temp;
	//a[middle]=temp;	
        quicksort2(a,i,low-1);//left
        quicksort2(a,low+1,j);//right

    }
	return;
}
static void quicksort(int *a, int left,int right)
{
	printf("leverage func %s, left=%d,right=%d\n",__func__,left,right);
    if(left<right)
    {
        int middle = (left+right)/2;
	//divide it into two part. [left,middle-1]<a[middle]; [middle+1,right]>=a[middle];
	int i=left,j=right;
	int temp=a[middle];
	while(i<j){
		while((a[i]<temp) && (i<middle))
			i++;
		if(i<middle){
			swap(a,i,middle);
		//	a[middle]=a[i];
			middle=i;
		}
		while(a[j]>=temp && (j>middle))
			j--;
		if(j>middle){
			swap(a,j,middle);
		//	a[middle]=a[j];
			middle=j;
		}
	}
	//a[middle]=temp;	
        quicksort(a,left,middle-1);//left
        quicksort(a,middle+1,right);//right

    }
	return;
}

static void quick_sort(int *p, int size){
	printf("leverage func %s, size=%d\n",__func__,size);
	quicksort2(p,0,size-1);
	return ;
}

static void adjust_top_down(int *p, int i, int size){
	int temp=p[i];
	
	int j=2*i+1;
	while(j<=size){
		if((j+1<=size) &&(p[j]>p[j+1]))
			j++;
		if(temp>p[j]){
			p[i]=p[j];
			i=j;
			j=2*j+1;
		}else
			break;
	}
	p[i]=temp;
	return;
}
static void adjust_top_down2(int *p, int i, int size){
	int temp=i;
	
	if(2*i+1>size)
		return;
	int min=2*i+1;
	if((2*i+2<=size) && (p[2*i+1]>p[2*i+2]))
		min=2*i+2;
	if(p[i]>p[min]){
		swap(p,i,min);
		adjust_top_down(p,min,size);
	}
	return;
}
static void adjust_heap(int *p, int size){
	for(int i=(size-1)/2; i>=0;i--){
		adjust_top_down(p, i, size);
		//print(p,size+1);
	/*	int min_index=2*i+1;
		if((2*i+2<=size) && (p[2*i+1]>p[2*i+2]))
			min_index=2*i+2;

		if(p[i]>p[min_index])
			swap(p,i,min_index);
	*/
	}
	return;

}

static void heap_sort(int *p, int size){
	printf("leverage func %s, size=%d\n",__func__,size);
	adjust_heap(p,size-1);
	printf("before the output sort:\n");print(p,size);
	for(int i=size-1;i>0;i--){
		printf("%d ",p[0]);
		swap(p,0,i);
		//adjust_heap(p,i-1);
		adjust_top_down(p,0,i-1);
	}
	printf("%d \n", p[0]);
	return ;
}
int main(int argc,char **argv)
{
	int inp[]={10,8,5,7,9,3,2,-1,1,6,4,7,-2};
//	int inp[]={1, 2,3,4,5,6,7, 8,9,10,11,12,13};
//	int inp[]={13,12,11,10,9,8,7,6,5,4,3,2,1};
	//int inp[]={10,8,5,9,3,2,-1,1,6,4,7,-2};
	int size= sizeof(inp)/sizeof(int);
	if(argc==1){
		printf("./a.out choose\n"
			"0: bubble_sort\n"
			"1: insert_sort\n"
			"2: select_sort\n"
			"3: shell_sort\n"
			"4: merge_sort\n"
			"5: quick_sort\n"
			"6: heap_sort\n"
			);
		return -1;
	}
	int choose=*argv[1]-'0';
	printf("choose=%d\n",choose);
	
	printf("before the sort:\n");print(inp,size);
	switch(choose){
	case 0:
		bubble_sort(inp,size);
		break;
	case 1:
		insert_sort(inp, size);
		break;
	case 2:	
		select_sort(inp,size);
		break;
	case 3:
		shell_sort(inp,size);
		break;
	case 4:
		merge_sort(inp,size);
		break;
	case 5:
		quick_sort(inp,size);
		break;
	case 6:
		heap_sort(inp,size);
		break;
	default:
		printf("unsupported choose\n");
		break;
	}
	printf("after the sort:\n");print(inp,size);

	return 0;
}
