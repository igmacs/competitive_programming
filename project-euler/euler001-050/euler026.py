# 1/d is not periodic iff 10^n % d = 0 for some n

# Since there are only d possible reminders and 10^(k+1) % d = (10^k %
# d)*10 % d, we can conclude that a rational number is periodic iff
# there is a cycle in the sequence of 10^i % d. It's relatively easy
# to check that that cycle length must be the same as the period
# length.

max_length = 0
max_d = 0

for d in range(1, 1000):
    mods = {}
    mod = 10 % d
    it = 0
    while mod != 0 and mod not in mods:
        mods[mod] = it
        it += 1
        mod = mod*10 % d
    if mod != 0 and it - mods[mod] > max_length:
        max_length = it - mods[mod]
        max_d = d

print(max_d)

# I think we can prove that cycle-length(1/p*q) =
# max(cycle-length(1/p), cycle-length(1/q)), and therefore the
# solution must be a prime (and higher than 500 if it's going to be
# unique)
