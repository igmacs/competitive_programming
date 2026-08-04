# If we include n itself in the list of divisors, we would be talking
# about the better known divisor function, and the equations would be
# instead d(a)-a = b and d(b)-b = a => d(a) = d(b) = a+b

# The divisor function is multiplicative (i.e., d(n*m) = d(n) * d(m)
# if n and m are coprime), and and d(p^k) = sum p^k = (p^(k+1)-1) / p-1, so we
# can use both facts to compute d more efficiently

import math

limit = 10000

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


sum_pairs = 0
for a in range(2,limit):
    b = ds[a] - a
    if a < b < limit and ds[b] == ds[a]:
        sum_pairs += a + b

print(sum_pairs)
