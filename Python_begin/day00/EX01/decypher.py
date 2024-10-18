import sys

countline = 0
flag = 0
if (len(sys.argv) == 2):
    for line in sys.argv[1]:
        if (countline == 0 and line[0:1] != ' '
            or flag == 1 and line[0:1] != ' '):
            print (line[0:1], end = "")
            flag = 0
        if (line == ' '):
            flag = 1
        countline =+ 1
    print("")
else:
    print("Error! Enter one line!")
