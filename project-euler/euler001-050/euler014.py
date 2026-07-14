import array

cache_below_one_million = array.array('L', (0 for _ in range(10**6)))
cache_above_one_million = {}

def get_cached(n):
    if n < 1000000:
        return cache_below_one_million[n] or None
    else:
        return cache_above_one_million.get(n, None)

def save_cached(n, m):
    if n < 1000000:
        cache_below_one_million[n] = m
    else:
        cache_above_one_million[n] = m


# Would be much simpler with recursion, but Python doesn't have tail
# call optimization
def chain_length(n):

    seq = [n]

    while n != 1:

        if cached := get_cached(n):
            break

        if n % 2 == 0:
            n = n//2
        else:
            n = n*3 + 1

        seq.append(n)

    if not cached:
        cached = 0

    for i, k in enumerate(seq):
        save_cached(k, cached + len(seq) - i)

    return len(seq) + cached

max_length = 0
for i in range(2,1000000):
    l = chain_length(i)
    if l > max_length:
        max_length = l
        ret = i

print(ret)
