import itertools

a = [1, 2, 3, 4, 5, 6]
outcomes = list(itertools.product(a, a))

print(outcomes)
print(len(outcomes))
