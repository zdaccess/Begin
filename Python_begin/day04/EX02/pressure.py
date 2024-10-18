import time

def emit_gel(step):
    result = 30
    while True:
        sign = yield result
        result += step * sign
        if result > 100:
            print("Error! Value greater than 100!")
            exit(0)

def valve(gen: emit_gel):
    sign = 1
    result = next(gen)
    while True:
        try:
            result = gen.send(sign)
            if result < 20 or result > 80:
                sign *= -1
            if result < 10 or result > 90:
                print("The value is greater than 90 or less than 10!")
                gen.close()
                exit(0)
            print(result)
            time.sleep(0.5)
        except StopIteration:
            break
        except:
            pass

if __name__ == "__main__":
    gen = emit_gel(5)
    valve(gen)
