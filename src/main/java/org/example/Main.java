package org.example;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String text = "BLA HELLO Dasha";
        RSAKey key = new RSAKey(PrimaryTest.MillerRabin, 0.95, 1024);;
        try {
            key.generateKey();
        } catch (Exception e) {
            System.out.println("Error in key generation");
            e.printStackTrace();
            System.exit(0);
        }
        RSA rsa = new RSA(key);
        byte[] encrypt = rsa.encrypt(text.getBytes());
        System.out.println(new String(encrypt));
        System.out.println(rsa.decrypt(encrypt));
    }
}