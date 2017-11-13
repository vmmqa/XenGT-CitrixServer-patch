import itertools

a = ["Tom", "Lee", "King", "James"]
outcomes = list(itertools.combinations(a, 2))

print(outcomes)
print(len(outcomes))
