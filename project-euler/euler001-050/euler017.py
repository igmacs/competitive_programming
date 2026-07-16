def number_letters(n):
    match n:
        case 0:
            return 0
        case 1 | 2 | 6 | 10:
            return 3
        case 4 | 5 | 9:
            return 4
        case 3 | 7 | 8 | 40 | 50 | 60:
            return 5
        case 11 | 12 | 20 | 30 | 80 | 90:
            return 6
        case 15 | 16 | 70:
            return 7
        case 13 | 14 | 18 | 19:
            return 8
        case 17:
            return 9
        case 1000:
            return 11
        case n if n >= 100:
            dec = number_letters(n%100)
            return number_letters(n//100) + 7 + (dec + 3 if dec else 0)
        case n if n>20:
            return number_letters(n%10) + number_letters(n-n%10)

print(sum(number_letters(n) for n in range(1, 1001)))
