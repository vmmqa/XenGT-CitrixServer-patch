#include <iostream>

using namespace std;

class teacher
{
	int age;
	teacher & m_teacher;

};

class student
{
	int age;
	short & weight;
};


int main()
{
	short *b;

	cout<<"size teacher="<<sizeof(teacher)<<",int="<<sizeof(int)
	<<",size short="<<sizeof(short)<<",pointer="<<sizeof(b)<<endl;

	cout<<"size student="<<sizeof(student)<<endl;
	return 0;
}
