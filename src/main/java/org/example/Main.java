package org.example;

import org.example.rsa.RSACipher;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        RSACipher rsaCipher = new RSACipher();
        rsaCipher.generateClientKeys();
        System.out.println(rsaCipher.getPublicKey());
    }
}