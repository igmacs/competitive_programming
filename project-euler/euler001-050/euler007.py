primes = [2,3,5,7]

def is_prime(n):
    for prime in primes:
        if candidate % prime == 0:
            return False
    return True

while len(primes) < 10001:
    candidate = primes[-1] + 2
    while not is_prime(candidate):
        candidate += 2
    primes.append(candidate)

print(primes[-1])
