A = set([1, 2, 3, 4])
B = set([3, 4, 5, 6])

print(A & B) # intersection
print(A | B) # union
print(A - B) # difference, element in A, and not in B
print(A ^ B) # symmetric difference, (A | B) - (A & B)
