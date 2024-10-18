import sys

countline = 0
sumline = ""
arg = 0
if (len(sys.argv) == 2 and sys.argv[1].isdigit()):
    arg = int(sys.argv[1])
else:
    print("Error! Enter one numeric argument!")
for line in sys.stdin.read():
    if (line == '\n' and countline < arg):
        if (sumline[0:5] == "00000" and sumline[5:6] != "0"
            and len(sumline) == 32):
            print(sumline)
        sumline = ""
        countline += 1
    else:
        sumline = sumline + line
if (countline < arg):
    if (sumline[0:5] == "00000" and sumline[5:6] != "0"
        and len(sumline) == 32):
        print(sumline)
