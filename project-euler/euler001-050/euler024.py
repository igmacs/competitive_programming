from math import factorial

target = 1000000 - 1
elems = list(range(10))
sol = []

while len(elems) > 1:
    el, target = divmod(target, factorial(len(elems)-1))
    sol.append(elems[el])
    elems = elems[:el] + elems[el+1:]

sol += elems
print(''.join(map(str, sol)))
