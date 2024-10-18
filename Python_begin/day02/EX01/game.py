from collections import Counter

class Player(object):
    def __init__(self, name):
        self.type = name
        self.opponents_choise = []
        self.player_choise = True
        self.savechoise = 0

    def opponents_action(self, choise: bool):
        self.opponents_choise.append(choise)

    def player_action(self) -> bool:
        pass

class Cheater(Player):
    def __init__(self, type="Cheater"):
        super(Cheater, self).__init__(type)
        self.player_choise = False

    def opponents_action(self, choise: bool):
        super(Cheater, self).opponents_action(choise)

    def player_action(self):
        return self.player_choise

class Cooperator(Player):
    def __init__(self, type = "Cooperator"):
        super(Cooperator, self).__init__(type)
        self.player_choise = True

    def opponents_action(self, choise: bool):
        super(Cooperator, self).opponents_action(choise)

    def player_action(self):
        return self.player_choise

class Copycat(Player):
    def __init__(self, type="Copycat"):
        super(Copycat, self).__init__(type)

    def opponents_action(self, choise: bool):
        self.player_choise = choise

    def player_action(self):
        return self.player_choise

class Grudger(Player):
    def __init__(self, type="Grudger"):
        super(Grudger, self).__init__(type)
        self.savechoise = 0

    def opponents_action(self, choise: bool):
        if not choise and self.savechoise == 0:
            self.player_choise = choise
            self.savechoise = 1

    def player_action(self):
        return self.player_choise

class Detective(Player):
    def __init__(self, type="Detective"):
        super(Detective, self).__init__(type)
        self.step = 0
        self.copycat = Copycat()
        self.cheater = Cheater()

    def opponents_action(self, choise: bool):
        if not choise and self.savechoise == 0:
            self.copycat.opponents_action(choise)
            self.player_choise = self.copycat.player_choise
            self.savechoise = 1
        elif choise and self.savechoise == 0:
            if self.step == 0:
                self.opponents_choise.append(False)
                self.opponents_choise.append(True)
                self.opponents_choise.append(True)
            if self.step < 3:
                self.player_choise = self.opponents_choise[self.step]
            if self.step > 2:
                self.player_choise = self.cheater.player_choise
            self.step += 1
        elif self.savechoise == 1:
            self.copycat.opponents_action(choise)
            self.player_choise = self.copycat.player_choise

    def player_action(self):
        return self.player_choise

class Mafia(Player):
    def __init__(self, type="Mafia"):
        super(Mafia, self).__init__(type)
        self.player_choise = False
        self.cheater = Cheater()
        self.grudger = Grudger()
        self.detective = Detective()
        self.step = 0

    def opponents_action(self, choise: bool):
        if not choise:
            self.cheater.opponents_action(choise)
            self.player_choise = self.cheater.player_choise
        else:
            if self.step % 2 == 0:
                self.grudger.opponents_action(False)
                self.player_choise = self.grudger.player_choise
            else:
                self.detective.opponents_action(True)
                self.player_choise = self.detective.player_choise
        self.step += 1

    def player_action(self):
        return self.player_choise

class Game(object):
    def __init__(self, matches=10):
        self.matches = matches
        self.registry = Counter()

    def play(self, player1: Player, player2: Player):
        self.registry.update({player1.type: 0, player2.type: 0})
        for step in range(self.matches):
            player1_choise = player1.player_action()
            player2_choise = player2.player_action()
            if player1_choise:
                if player2_choise:
                    self.registry.update({player1.type: 2, player2.type: 2})
                else:
                    self.registry.update({player1.type: -1, player2.type: 3})
            else:
                if player2_choise:
                    self.registry.update({player1.type: 3, player2.type: -1})
            player1.opponents_action(player2_choise)
            player2.opponents_action(player1_choise)

    def top3(self):
        for player, score in self.registry.most_common(3):
            print(player.lower(), score)

if __name__ == "__main__":
    game = Game()
    game.play(Cooperator(), Cheater())
    game.play(Cooperator(), Copycat())
    game.play(Cooperator(), Grudger())
    game.play(Cooperator(), Detective())
    game.play(Cheater(), Copycat())
    game.play(Cheater(), Grudger())
    game.play(Cheater(), Detective())
    game.play(Copycat(), Grudger())
    game.play(Copycat(), Detective())
    game.play(Grudger(), Detective())
    game.play(Cheater(), Mafia())
    game.play(Copycat(), Mafia())
    game.play(Cooperator(), Mafia())
    game.play(Grudger(), Mafia())
    game.play(Detective(), Mafia())
    game.top3()