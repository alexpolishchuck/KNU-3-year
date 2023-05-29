class RSA:
    def start(self):

        d = self.A_find_d()

        print(f'd = {d}')

        message = 657850

        print(f'message = {message}')

        encrypted = self.B_encrypt_message(message)

        print(f'encrypted = {encrypted}')

        decrypted = self.A_decrypt_message(encrypted, d)

        print(f'decrypted = {decrypted}')

    def A_find_d(self):
        x = (self.P - 1) * (self.Q - 1)

        res = self.find_inverse(self.E, x)

        return res

    def B_encrypt_message(self, message):
        return pow(message, self.E) % (self.P * self.Q)

    def A_decrypt_message(self, encrypted_message, d):

        N = self.P * self.Q
        a = pow(encrypted_message, d) % N
        return a % N

    def find_inverse(self, val, modulo):

        g, inverse, mod_coeff = self.gcd(val, modulo, modulo)

        if g != 1:
            raise Exception("find_inverse. Can't find inverse.")

        res = (inverse % modulo + modulo) % modulo

        return res

    def gcd(self, a, b, modulo):
        if a == 0:
            return b, 0, 1

        g, inverse, mod_coeff = self.gcd(b % a, a, modulo)
        x1 = inverse
        y1 = mod_coeff

        x = y1 - (b // a) * x1
        y = x1

        return g, x, y

    E = 1289
    Q = 1283
    P = 1279
