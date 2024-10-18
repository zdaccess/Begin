import sys

linenmb = 0;
sumstr = ""
status = ""
for str in sys.stdin.read():
    if (str == '\n'):
        if len(sumstr) == 5:
            if (linenmb == 0 and sumstr[0:1] == '*' and sumstr[4:5] == '*'
                and sumstr[1:2] != '*' and sumstr[2:3] != '*'
                and sumstr[3:4] != '*'):
                sumstr = ""
                linenmb += 1;
            elif (linenmb == 1 and sumstr[0:1] == '*' and sumstr[4:5] == '*'
                and sumstr[1:2] == '*' and sumstr[2:3] != '*'
                and sumstr[3:4] == '*'):
                sumstr = ""
                linenmb += 1;
            elif (linenmb == 2):
                status = "Error"
                linenmb += 1;
                break
            else:
                status = "False"
                break
        else:
            status = "Error"
            break;
    else:
        sumstr = sumstr + str
if (linenmb == 2 and status == ""):
    if (len(sumstr) == 5):
        if (sumstr[0:1] == '*' and sumstr[1:2] != '*' and sumstr[2:3] == '*'
            and sumstr[3:4] != '*' and sumstr[4:] == '*'):
            status = "True"
    else:
        status = "Error"
print(status)
