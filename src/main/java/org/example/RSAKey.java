package org.example;

import java.math.BigInteger;
import java.util.Random;

public class RSAKey {
    final double REQUIRED_PRECISION = 0.95;
    IPrimaryTest usedTest;
    double precision;
    int bitLength;

    BigInteger n;
    BigInteger e;
    BigInteger d;

    RSAKey(PrimaryTest nameTest, double precision, int bitLength) {
        if (nameTest == PrimaryTest.Ferma) {
            this.usedTest = new TestFerma();
        } else if (nameTest == PrimaryTest.SolovayStrassen) {
            this.usedTest = new TestSolovayStrassen();
        } else if (nameTest == PrimaryTest.MillerRabin) {
            this.usedTest = new TestMillerRabin();
        }
        this.precision = precision;
        this.bitLength = bitLength;
    }

    void generateKey() throws Exception {
        Random random = new Random(System.currentTimeMillis());
        BigInteger p;
        BigInteger q;
        do {
            p = new BigInteger(bitLength, random);
        } while (!usedTest.test(p, REQUIRED_PRECISION) || p.bitLength() != bitLength);
        do {
            q = new BigInteger(bitLength, random);
        } while (!usedTest.test(q, REQUIRED_PRECISION) || q.bitLength() != bitLength);

        this.n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = BigInteger.valueOf(65537L);
        this.d = solve(phi, e);
    }

    private static BigInteger solve(BigInteger a, BigInteger b)
    {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger lastx = BigInteger.ONE;
        BigInteger lasty = BigInteger.ZERO;
        BigInteger temp;
        while (!b.equals(BigInteger.ZERO))
        {
            BigInteger q = a.divide(b);
            BigInteger r = a.remainder(b);

            a = b;
            b = r;

            temp = x;
            x = lastx.subtract(q.multiply(x));
            lastx = temp;

            temp = y;
            y = lasty.subtract(q.multiply(y));
            lasty = temp;
        }
        return lasty;
    }
}
