def factorial(n):
    return 1 if n==1 else n*factorial(n-1)

def number_combinations(n, m):
    return factorial(n) // (factorial(m) * factorial(n-m))

# There are 40 movements to be done, 20 right and 20 down. The number
# of routes is the number of ways to place the 20 movements right (or
# down) in the 40 total movements, which is (40 | 20)

print(number_combinations(40, 20))
