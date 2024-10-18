class Key:
     def __init__(self):
          self.passphrase = "zax2rulez"
          self.lenght = 1337
          self.nmb = 0

     def __len__(self):
          return self.lenght

     def __getitem__(self, item: int):
          count = 0
          if item <= 404:
               while item > 0:
                    item = int(item / 10)
                    count += 1
          else:
               count = item
          return count

     def __gt__(self, item: int):
          if isinstance(item, int):
               if item >= 9000:
                    self.nmb = item + 1
               if self.nmb > item:
                    return True
               else:
                    return False
               
     def __lt__(self, item: int):
          if isinstance(item, int):
               if item <= 9000:
                    self.nmb = 0
               if self.nmb < item:
                    return False
               else:
                    return True

     def __str__(self):
          return "GeneralTsoKeycard"

if __name__ == "__main__":
     key = Key()
     assert len(key) == 1337
     assert key[404] == 3
     assert key > 9000
     assert key.passphrase == "zax2rulez"
     assert str(key) == "GeneralTsoKeycard"
