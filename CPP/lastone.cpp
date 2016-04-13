#include <iostream>
#include <string>

using namespace std;
int lastone(int m,int n)
{

	int *p=new int[m+1];
	int ret;
	int count=1;
	int found=0;

	for(int i=1;i<m+1;i++)
		p[i]=0;
	
	for(int i=1;i<m+1;i++,count++)
	{
		for(
			
	}
	
	delete [] p;

	ret=3;
	return ret;

}

int main()
{

	int i;
	for(i=3;i<10;i++)
		cout<<"get it"<<lastone(i,3)<<endl;
}

