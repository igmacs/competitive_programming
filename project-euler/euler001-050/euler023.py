# first hald of the code copied from exercise 21

import math
import itertools

limit = 28123

sieve = [False]*limit # Eratosthenes sieve, but also used to check
                      # that a multiple of p^k hasn't already been
                      # processed as a multiple of p^(k+1)

ds = [1]*limit

for p in range(2, limit):

    if sieve[p]:
        continue

    sieve[p::p] = [False]*((limit -1) // p)

    max_k = int(math.log(limit, p))
    pk = p**max_k
    while pk > 1:
        n = pk
        while n < limit:
            if not sieve[n]:
                sieve[n] = True
                ds[n] *= (pk*p - 1) // (p-1)
            n += pk
        pk //= p

numbers = list(range(limit))

abundant = [n for n in range(12, limit) if ds[n] - n > n]


for n,m in itertools.product(abundant, abundant):
    if n+m < limit:
        numbers[n+m] = 0


print(sum(numbers))
