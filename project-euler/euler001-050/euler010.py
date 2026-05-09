# Naive approach: too slow

# primes = [2,3,5,7]

# def is_prime(n):
#     for prime in primes:
#         if candidate % prime == 0:
#             return False
#     return True


# candidate = 11

# while (candidate < 2000000):
#     candidate += 2
#     if is_prime(candidate):
#         primes.append(candidate)
#         print(candidate)

# print(sum(primes))



# Second approach: Sieve of Eratosthenes, much faster

limit = 2000000
primes = []
numbers = list(range(2,limit))

for n in numbers:
    if n > 0:
        primes.append(n)
        for j in range(1, -(-limit//n)):
            numbers[n*j - 2] = 0

print(sum(primes))
