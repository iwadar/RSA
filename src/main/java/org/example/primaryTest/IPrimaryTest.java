package org.example.primaryTest;

import java.math.BigInteger;
import java.util.Random;

public interface IPrimaryTest {
    boolean test(BigInteger p, double precision) throws Exception;
    static BigInteger getRandomBase(BigInteger n)
    {
        Random random = new Random(System.currentTimeMillis());
        BigInteger a;
        do {
            a = new BigInteger(n.bitLength(), random);
        } while (a.compareTo(n) >= 0 || a.compareTo(BigInteger.ONE) <= 0);
        return a;
    }
}
