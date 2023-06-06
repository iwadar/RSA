package org.example;

import java.math.BigInteger;

public class TestFerma implements IPrimaryTest{
    @Override
    public boolean test(BigInteger n, double precision) throws Exception {
        if (n.equals(BigInteger.TWO))
        {
            return true;
        }
        if (n.compareTo(BigInteger.TWO) < 0)
        {
            return false;
        }
        if (n.and(BigInteger.ONE).equals(0)) {
            return false;
        }
        if (precision < 0.5 || precision >= 1)
        {
            System.out.println("Error!");
            throw new Exception("Precision is less than 0.5!");
        }
        double k = Math.log(1 / (1 - precision)) / Math.log(2);
        if (Math.abs(k - (int)k) > 0)
        {
            k++;
            k = Math.round(k);
        }
        BigInteger a;
        for (long i = 0; i < k; i++)
        {
            a = IPrimaryTest.getRandomBase(n);
            if (!a.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }
}
