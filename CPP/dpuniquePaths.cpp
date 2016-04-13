#include <iostream>
using namespace std;

int UnPath(int a,int b)
{

	int num[a][b];
	int i,j;
	if((a<1) || (b<1))
		return -1;

	for(i=0;i<a;i++)
		num[i][0]=1;
	
	for(i=0;i<b;i++)
		num[0][i]=1;

	for(i=1;i<a;i++)
		for(j=1;j<b;j++)
			num[i][j]=num[i-1][j]+num[i][j-1];

	return num[i-1][j-1];
}

		


int main(void)
{
	int a,b;
	cout<<"please input the row and column"<<endl;
	cin>>a>>b;

	cout<<"a="<<a<<",b="<<b<<endl;
	cout<<"ret="<<UnPath(a,b)<<endl;
	return 0;

}
