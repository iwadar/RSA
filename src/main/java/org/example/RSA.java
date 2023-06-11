package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA {
    RSAKey key;
    RSA(RSAKey key) {
        this.key = key;
    }

    void setKey(RSAKey key) {
        this.key = key;
    }
    public byte[] encrypt(byte[] data) {
        byte[] copy = new byte[data.length + 1];
        copy[0] = 127;
        System.arraycopy(data, 0, copy, 1, data.length);
        BigInteger m = new BigInteger(copy);
        BigInteger x = m.modPow(key.e, key.n);
        return x.toByteArray();
    }

    public String decrypt(byte[] data) {

        BigInteger x = new BigInteger(data);

        BigInteger m = x.modPow(key.d, key.n);

        int length = m.toByteArray().length;
        byte[] copy = new byte[length - 1];
        System.arraycopy(m.toByteArray(), 1, copy, 0, length - 1);

        return new String(copy, StandardCharsets.UTF_8);
    }
}
