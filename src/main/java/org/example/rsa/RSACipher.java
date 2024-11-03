package org.example.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class RSACipher {

    private BigInteger n, phi, e, d;
    private BigInteger serverE, serverN;
    private final SecureRandom random = new SecureRandom();

    public void generateClientKeys() {
        int[] primes = {11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

        BigInteger p, q;
        do {
            p = BigInteger.valueOf(primes[random.nextInt(primes.length)]);
            q = BigInteger.valueOf(primes[random.nextInt(primes.length)]);
        } while (p.equals(q));

        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.valueOf(3);
        while (phi.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.valueOf(2));
        }
        d = e.modInverse(phi);
    }

    public void setServerPublicKey(BigInteger serverE, BigInteger serverN) {
        this.serverE = serverE;
        this.serverN = serverN;
    }

    private BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger mod) {
        return base.modPow(exponent, mod);
    }

    private BigInteger encodeChar(char character) {
        return modPow(BigInteger.valueOf(character), d, n);
    }

    private char decodeChar(BigInteger code) {
        return (char) modPow(code, serverE, serverN).intValue();
    }

    public String encodeString(String str) {
        BigInteger[] encodedString = new BigInteger[str.length()];
        for (int i = 0; i < str.length(); i++) {
            encodedString[i] = encodeChar(str.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        for (BigInteger symbol : encodedString) {
            String hexString = symbol.toString(16);
            sb.append(hexString).append(":");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public String decodeString(String encodedString) {
        BigInteger[] encodedSymbols = Arrays.stream(encodedString.split(":"))
                .map(hexString -> new BigInteger(hexString, 16))
                .toArray(BigInteger[]::new);

        StringBuilder decodedString = new StringBuilder();
        for (BigInteger code : encodedSymbols) {
            decodedString.append(decodeChar(code));
        }
        return decodedString.toString();
    }

    public String getPublicKey() {

        if (e == null || n == null)
            throw new IllegalStateException("Client keys not generated");

        return  e + "," + n;
    }
}
