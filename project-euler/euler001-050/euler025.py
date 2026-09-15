fib1 = 1
fib = 1
n = 2
while fib < 10**999:
    fib1, fib = fib, fib1+fib
    n+=1

print(n)
