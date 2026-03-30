fib2 = 2
fib1 = 1
fib0 = 1
acc = 0

while fib2 < 4000000:
    acc += fib2
    fib0 = fib2 + fib1
    fib1 = fib0 + fib2
    fib2 = fib0 + fib1

print(acc)
