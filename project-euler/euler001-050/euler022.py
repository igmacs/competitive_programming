import requests

names = sorted(requests.get("https://projecteuler.net/resources/documents/0022_names.txt").text.split(','))

s = 0

for i, name in enumerate(names):
    v = sum(ord(c)-64 for c in name[1:-1])
    s += (i+1)*v

print(s)


