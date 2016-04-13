#include <iostream>
using namespace std;


void quick_sort(int *a, int low1, int high1)
{
	if(low1>=high1)
		return;

	int low=low1,high=high1;
	int val=a[low];
	while(low<high)
	{
		while((a[high]>=val)&&(low<high))
		{
			high--;
		}
		if(high>low)
			a[low++]=a[high];
		else
			break;

		while((a[low]<val)&&(low<high))
		{
			low++;
		}
		if(high>low)
			a[high--]=a[low];
		else
			break;

	}
	a[low]=val;
	cout<<"low="<<low<<"val="<<val<<"high="<<high<<"low1="<<low1<<"high1="<<high1<<endl;


	quick_sort(a,low1,low-1);
	quick_sort(a,low+1,high1);


}


int main()
{
	int a[10]={1,3,5,6,7,9,2,4,8,10};

	quick_sort(a,0,9);

	for(int i=0;i<10;i++)
		cout<<a[i]<<",";
	cout<<endl;
	

}

