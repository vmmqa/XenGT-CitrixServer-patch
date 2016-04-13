#include <queue>
#include <vector>
#include <list>
#include <cstdio>
using namespace std;

int main()
{
	//可以使用list作为单向队列的容器，默认是使用deque的。
	queue<int, list<int> > a;
	queue<int>        b;
	int i;

	//压入数据
	for (i = 0; i < 10; i++)
	{
		a.push(i);
		b.push(i);
	}

	//单向队列的大小
	printf("%d %d\n", a.size(), b.size());

	//队列头和队列尾
	printf("%d %d\n", a.front(), a.back());
	printf("%d %d\n", b.front(), b.back());

	//取单向队列项数据并将数据移出单向队列
	while (!a.empty())
	{
		printf("%d ", a.front());
		a.pop();
	}
	putchar('\n');

	while (!b.empty())
	{
		printf("%d ", b.front());
		b.pop();
	}
	putchar('\n');
	return 0;

}
