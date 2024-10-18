def squeak_decorator(func):
    def all(*args, **kwargs):
        print("SQUEAK")
        return func(*args, **kwargs)
    return all

@squeak_decorator
def add_ingot(purse):
    if len(purse) == 0:
        new_purse = {"gold_ingots": 0}
    else:
        new_purse = {"gold_ingots": int(purse.get("gold_ingots")) + 1}
    return new_purse

@squeak_decorator
def get_ingot(purse):
    return purse

@squeak_decorator
def empty(purse):
    new_purse = {}
    return new_purse

if __name__ == "__main__":
    purse = {"gold_ingots": 0}
    result = add_ingot(get_ingot(add_ingot(empty(purse))))
    print(result)
