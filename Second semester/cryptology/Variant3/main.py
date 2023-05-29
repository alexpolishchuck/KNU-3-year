import Diffie_Hellman_Protocol
import RSA

#Oleksandr Polishchuk's cryptology lab
# Variant 13

if __name__ == '__main__':
    rsa = RSA.RSA()
    rsa.start()

    dhp = Diffie_Hellman_Protocol.DHP()
    dhp.start()

