
import itertools

a = ["Tom", "Lee", "King", "James"]
outcomes = list(itertools.permutations(a, 2))

print(outcomes)
print(len(outcomes))
