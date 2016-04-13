#include <queue>
#include <list>
#include <cstdio>
using namespace std;
int main()
{
	//优先级队列默认是使用vector作容器。
	priority_queue<int> a;
	int i;
	//压入数据
	for (i = 0; i < 10; i++)
	{
		a.push(i * 2 - 5);
		//b.push(i); //编译错误
	}
	//优先级队列的大小
	printf("%d\n", a.size());
	//取优先级队列数据并将数据移出队列
	while (!a.empty())
	{
		printf("%d ", a.top());
		a.pop();
	}
	putchar('\n');
	return 0;

}
