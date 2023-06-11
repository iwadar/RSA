package org.example.primaryTest;

import java.math.BigInteger;

public class TestSolovayStrassen implements IPrimaryTest{
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
        if (n.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
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
        for (int i = 0; i < k; i++) {
            BigInteger a = IPrimaryTest.getRandomBase(n);
            if (!a.gcd(n).equals(BigInteger.ONE)) {
                return false;
            }
            BigInteger exponent = n.subtract(BigInteger.ONE).divide(BigInteger.TWO);
            int jacobi = Symbols.J(a, n);
            BigInteger a1 = a.modPow(exponent, n);

            if (jacobi == 1) {
                if (!a1.equals(BigInteger.ONE)) {
                    return false;
                }
            }
            else if (jacobi == -1) {
                if (!a1.equals(n.subtract(BigInteger.ONE))) {
                    return false;
                }
            }
        }
        return true;
    }
}
