max_factor = 1
factor_candidate = 2
n = 600851475143

while n >= factor_candidate:
    if n % factor_candidate == 0:
        n /= factor_candidate
        max_factor = factor_candidate
    else:
        factor_candidate += 1

print(max_factor)
