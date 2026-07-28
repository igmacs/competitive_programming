day = 1
month = 1
year = 1900
weekday = 0

def new_day():
    global day, month, year, weekday

    if day == 28 and month == 2 and (year%4 != 0 or year == 1900):
        day = 0
        month +=1
    if day == 29 and month == 2:
        day = 0
        month +=1
    if day == 30 and month in [4,6,9,11]:
        day = 0
        month +=1
    if day == 31:
        day = 0
        month += 1


    day += 1

    if month == 13:
        month = 1
        year += 1

    weekday = (weekday + 1) % 7

while day != 1 or month != 1 or year != 1901:
    new_day()

count = 0
while day != 31 or month !=12 or year != 2000:
    if day == 1 and weekday == 6:
        count += 1
    new_day()

print(count)
