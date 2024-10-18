from random import randint

def turrets_generator():
    data = [0, 0, 0, 0, 0]
    for step in range(100):
        data[randint(0,4)] += 1

    def shoot(self):
        print("Shooting")

    def search(self):
        print("Searching")

    def talk(self):
        print("Talking")

    turret = {
        'neuroticism': data[0],
        'openness': data[1],
        'conscientiousness': data[2],
        'extraversion': data[3],
        'agreeableness': data[4],
        'shoot': shoot,
        'search': search,
        'talk': talk
    }

    Turret = type('Turret', (object,), turret)
    return Turret()

if __name__ == "__main__":
    turret = turrets_generator()
    print(type(turret))
    turret.shoot()
    turret.search()
    turret.talk()
    print(f"neuroticism: {turret.neuroticism}")
    print(f"openness: {turret.openness}")
    print(f"conscientiousness: {turret.conscientiousness}")
    print(f"extraversion: {turret.extraversion}")
    print(f"agreeableness: {turret.agreeableness}")
    print(f"summ = {turret.neuroticism + turret.openness 
                    + turret.conscientiousness + turret.extraversion 
                    + turret.agreeableness}")