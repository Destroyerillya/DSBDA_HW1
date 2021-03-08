from random import randrange
from datetime import timedelta
from datetime import datetime
import random
import re


def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)
    return start + timedelta(seconds=random_second)

warnings = ['0', '1', '2', '3', '4', '5', '6', '7']
start = input('input start date(EXAMPLE 03/05/2021 05:00:00): ')
if re.match("(0[1-9]|1[0-2])/(0[1-9]|2[0-9]|3[0-1])/20[0-9]{2} (0[0-9]|1[0-9]|2[0-3])(:(0[0-9]|[1-5][0-9])){2}", start) == None:
    print("BAD START DATE")
    exit(1)
end = input('input end date (EXAMPLE 03/05/2021 05:00:00): ')
if re.match("(0[1-9]|1[0-2])/(0[1-9]|2[0-9]|3[0-1])/20[0-9]{2} (0[0-9]|1[0-9]|2[0-3])(:(0[0-9]|[1-5][0-9])){2}", end) == None:
    print("BAD END DATE")
    exit(1)

d1 = datetime.strptime(start, '%m/%d/%Y %H:%M:%S')
d1_cpy = d1.replace(hour=23, minute=59, second=59)
d2 = datetime.strptime(end, '%m/%d/%Y %H:%M:%S')
qty_files = d2.day - d1.day
for i in range(qty_files + 1):
    f = open(str(i), 'wb')
    for j in range(300000):
        if random.choice([1,2,3,4,5,6,7,8,9,10]) != 1:
            war_res = str(random.choice(warnings))
            time_res = str(random_date(d1, d1_cpy))
            result_str = time_res + ',' + war_res + '\n'
            f.write(str.encode(result_str))
        else:
            f.write(b'DSBDA-2021\n')
            f.write(b'03/05/2021 05\n')
            f.write(b'05:00:00\n')
    f.close()
    d1 = d1.replace(day=d1.day + 1, hour=0, minute=0, second=0)
    if i != qty_files - 1:
        d1_cpy = d1_cpy.replace(day=d1_cpy.day + 1)
    else:
        d1 = d2.replace(hour=0, minute=0, second=0)
        d1_cpy = d2
