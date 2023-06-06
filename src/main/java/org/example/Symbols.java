package org.example;

import java.math.BigInteger;

public class Symbols {
    public static int legendre(BigInteger p, BigInteger a) {
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.TWO;
        BigInteger mod = null;

        // Первая проверка: a == 0 (mod p)
        if (a.mod(p).equals(zero)) {
            return 0;
        }

        // Вторая проверка: a == 1 (mod p)
        if (a.equals(one)) {
            return 1;
        }

        // Третья проверка: a == 2 (mod p)
        if (a.equals(two)) {
            mod = p.mod(BigInteger.valueOf(8));
            if (mod.equals(one) || mod.equals(BigInteger.valueOf(7))) {
                return 1;
            } else {
                return -1;
            }
        }

        // Четвертая проверка: a не кратно p и не равно 1
        BigInteger s = BigInteger.ZERO;
        BigInteger d = new BigInteger(a.toString());
        while (d.mod(two).equals(zero)) {
            s = s.add(one);
            d = d.divide(two);
        }
        if (s.mod(two).equals(zero)) {
            mod = p.mod(BigInteger.valueOf(8));
            if (mod.equals(one) || mod.equals(BigInteger.valueOf(7))) {
                return 1;
            } else {
                return -1;
            }
        }

        BigInteger r = d.mod(p);
        int result = 1;
        while (!r.equals(one) && !r.equals(zero)) {
            while (r.mod(two).equals(zero)) {
                r = r.divide(two);
                BigInteger mod8 = p.mod(BigInteger.valueOf(8));
                if (mod8.equals(BigInteger.valueOf(3)) || mod8.equals(BigInteger.valueOf(5))) {
                    result = -result;
                }
            }
            BigInteger c = r;
            r = p;
            p = c;
            if (r.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                result = -result;
            }
            r = r.mod(p);
        }
        if (r.equals(zero)) {
            return 0;
        } else {
            return result;
        }
    }

    public static int J(BigInteger n, BigInteger m) {
        if (n.mod(m).equals(BigInteger.ZERO)) {
            return 0; // m делит n
        }
        int j = 1;
        if (n.compareTo(BigInteger.ZERO) < 0) {
            n = n.negate(); // правило (b/n) = (-1)^((n-1)/2) * (1/n) * b
            if (m.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                j = -j; // n отрицательно и m по модулю находится по 3 модулю 4
            }
        }
        while (!n.equals(BigInteger.ZERO)) {
            while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) { // правило (2/n) = (-1)^((n^2-1)/8)
                n = n.divide(BigInteger.TWO);
                if (m.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || m.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))) {
                    j = -j;
                }
            }
            BigInteger temp = n; // Поменяем местами n и m, чтобы n было большим
            n = m;
            m = temp;

            if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && m.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) { // правило (a/b) = -(b/a)
                j = -j;
            }
            n = n.mod(m);

        }
        if (m.equals(BigInteger.ONE)) {
            return j;
        } else {
            return 0; // m не является взаимно простым с n
        }
    }
}
