# Brute force is enough, not need to optimize. If we needed to, there
# is probably a better exploration order which guarantees that as soon
# as we find one palindrome it will be the largest one and we can
# stop. Also, we can observe that the palindrome must be a product of
# 11 (assuming it has even digits, but I doubt the largest palindrome
# has only 5 digits).

def is_palindrome(n):
    return str(n) == ''.join(reversed(str(n)))

max_palindrome=0

for i in range(100,1000):
    for j in range(100,i+1):
        n = i*j
        if is_palindrome(n) and n>max_palindrome:
            max_palindrome = n

print(max_palindrome)
