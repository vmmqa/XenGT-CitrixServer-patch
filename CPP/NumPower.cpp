#include <iostream>
using namespace std;


int NumPower(int num, int power)
{
	if(power<0)
		return -1;
	int ret=1;

	while(power)
{
	if(power & 1)
		ret*=num;

	num*=num;
	power >>= 1;

}
	return ret;

}


int main(void)
{
	int x=3;
	for(int i=0;i<10;i++)
		cout<<"3 power "<<i<<"="<<NumPower(x,i)<<endl;

	return 0;

}
