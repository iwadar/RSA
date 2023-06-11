package org.example;

import org.example.primaryTest.*;

import java.math.BigInteger;
import java.util.Random;

public class RSAKey {
    final double REQUIRED_PRECISION = 0.95;
    final int WIENER_CRITERIA = 256;
    IPrimaryTest usedTest;
    double precision;
    int bitLength;

    BigInteger n;
    BigInteger e;
    BigInteger d;// = BigInteger.ZERO;

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
        this.d = BigInteger.ZERO;

//        чтобы прошла атака Винера надо раскомментить нижеследующий блок

//        p = new BigInteger("239");
//        q = new BigInteger("379");
//        this.n = p.multiply(q);
//        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
//        this.e = BigInteger.valueOf(17993L);
//        this.d = solve(phi, e);


        // комментить для прохождения атаки
        while (d.bitLength() < WIENER_CRITERIA) {
            do {
                p = new BigInteger(bitLength / 2, random);
            } while (!usedTest.test(p, REQUIRED_PRECISION) || p.bitLength() != bitLength / 2);
            do {
                q = new BigInteger(bitLength / 2, random);
            } while (!usedTest.test(q, REQUIRED_PRECISION) || q.bitLength() != bitLength / 2);
            this.n = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            this.e = BigInteger.valueOf(65537L);
            this.d = solve(phi, e);
        }
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
