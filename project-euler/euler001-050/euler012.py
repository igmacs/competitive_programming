# Triangular numbers are always are n(n+1)/2. If n is even, any
# divisor of n/2 will be a divisor of n, so it won't be a divisor of
# n-1 (gcd(n,n+1) is always 1). The same goes if it's n-1 the one that
# is even. So the total number of divisors is the product of the total
# number of divisors for each factor

# The number of divisors of n*m when gcd(n,m)=1, is number of divisors
# of n multiplied by number of divisors of m

# Then number of divisors of p^k with p a prime, is k+1

# With all that, we can just iterate over n and count the total number
# of divisors using dynamic programming

limit = 500

primes = [2]
n_divisors = [0,1,1]
n = 2

while True:

    if (
            (n % 2 == 0 and n_divisors[n//2]*n_divisors[n-1] > limit)
            or
            (n % 2 == 1 and n_divisors[n]*n_divisors[(n-1)//2] > limit)
    ):
            print(n*(n-1)//2)
            exit(0)

    n+=1
    nd = 2
    for p in primes:
        if n % p == 0:
            m,k = n//p,1
            while (m % p == 0):
                m //= p
                k += 1
            nd = n_divisors[m]*(k+1)
            break

    n_divisors.append(nd)

    if nd==2:
        primes.append(n)
