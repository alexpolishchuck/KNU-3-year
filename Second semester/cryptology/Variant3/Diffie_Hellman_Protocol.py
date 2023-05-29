import random


class DHP:

    def start(self):
        Alices_private_key = random.randint(2, 40)
        Bobs_private_key = random.randint(2, 40)

        Alices_public_key = self.compute_public_key(Alices_private_key)
        Bobs_public_key = self.compute_public_key(Bobs_private_key)

        Alices_symmetric_key = self.compute_symmetric_key(Bobs_public_key, Alices_private_key)
        Bobs_symmetric_key = self.compute_symmetric_key(Alices_public_key, Bobs_private_key)

        print(f'Alice\'s symmetric key = {Alices_symmetric_key}')
        print(f'Bob\'s symmetric key = {Bobs_symmetric_key}')
    def compute_public_key(self,power):
        return pow(self.G, power) % self.P

    def compute_symmetric_key(self,public_key, private_key):
        return pow(public_key, private_key) % self.P
    P = 449
    G = 877