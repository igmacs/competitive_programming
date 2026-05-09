# Maybe it can be done a smarter way, but brute force is enough

for a in range(1,333):
    for b in range(a+1,500):
        if a*a+b*b == (c := 1000-a-b)*c:
            print(a*b*c)
            exit(0)
