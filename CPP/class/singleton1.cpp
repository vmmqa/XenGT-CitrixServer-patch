#include <iostream>
using namespace std;

class Singleton{
	private:
		int test;
		Singleton() { cout<<"new"<<endl; test=0;}
		~Singleton() {cout<<"delete"<<endl;}

	public:
		static Singleton* GetInstance(){
			static Singleton singleton;
			return &singleton;
		}

		void SetValue(int v)
		{
			test=v;
		}

		int GetValue()
		{
			return test;
		}



};


int main()
{
	Singleton *single1=Singleton::GetInstance();
	Singleton *single2=Singleton::GetInstance();

	cout << "value 1: " << single1->GetValue() << endl;
	single1->SetValue(100);
	cout << "value 2: " << single2->GetValue() << endl;
	if( single2 == single1 )
		cout << "single1 and single2 is the same object." << endl;
	return 0;


}
