def sum_up_to(n):
    return int(n*(n+1)/2)

def sum_multiples_below(k,n):
    return k*sum_up_to(int((n-1)/k))

print(sum_multiples_below(3,1000) + sum_multiples_below(5,1000) - sum_multiples_below(15,1000))
