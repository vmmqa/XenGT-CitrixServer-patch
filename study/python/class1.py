class cl:
	def __init__(self, name):
		self.myname=name
	def myfunc1(self):
		print("hello"+self.myname)

c1=cl("libo")
c1.myfunc1()
