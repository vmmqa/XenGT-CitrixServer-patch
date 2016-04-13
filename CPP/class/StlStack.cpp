#include <stack>
#include <vector>
#include <list>
#include <iostream>
using namespace std;
int main()
{
	//可以使用list或vector作为栈的容器，默认是使用deque的。
	stack<int, list<int> >      a;
	stack<int, vector<int> >   b;
	int i;
	
	//压入数据
	for (i = 0; i < 10; i++)
	{
		a.push(i);
		b.push(i);
	}

	//栈的大小
	cout<<a.size()<<"size="<< b.size()<<endl;

	//取栈项数据并将数据弹出栈
	while (!a.empty())
	{
		cout<<a.top();
		a.pop();
	}

	cout<<endl;

	while (!b.empty())
	{
		cout<<b.top();
		b.pop();
	}
	cout<<endl;
	return 0;
}

