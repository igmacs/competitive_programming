def sumn(n):
    return n*(n+1)//2

def sumn2(n):
    return n*(n+1)*(2*n+1)//6

print(sumn(100)*sumn(100) - sumn2(100))
