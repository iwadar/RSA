package org.example;

import java.math.BigInteger;

public class WienerAttack {
    static int[] a;
    static int[][] kd;
    static int d = 0;
    static int guessD;

    public static int[] continuousFraction(BigInteger N, BigInteger e) {
        int[] a= new int[1000];
        a[0] = 0;
        BigInteger temp;
        int i = 1;
        while(N.divide(e).compareTo(BigInteger.valueOf(0)) > 0 ){
            temp = N;
            N = e;
            e = temp;
            BigInteger x = e.divide(N);
            a[i] = (int) Math.floor(x.doubleValue());
            e = e.subtract(N.multiply(BigInteger.valueOf(a[i])));
            i++;
            if (e.equals(BigInteger.valueOf(0))) {
                break;
            }
        }
        int[] a_ = new int[i];
        System.arraycopy(a, 0, a_, 0, i);
        return a_;
    }

    public static int[][] k_d(int[] a) {
        int[][] kd = new int[a.length][2];
        kd[1][0] = 1;
        kd[1][1] = a[1];

        for (int i = 2; i < a.length; i++) {
            int ar = 1, par = 1;
            int temp1 = a[i];
            for(int j = i; j > 1; j--) {
                par = a[j-1] * temp1 + ar;
                ar = temp1;
                temp1 = par;
            }
            kd[i][0] = ar;
            kd[i][1] = par;
        }
        return kd;
    }

    public static BigInteger f(BigInteger e, int k, int d){
        return (e.multiply(BigInteger.valueOf(d)).subtract(BigInteger.valueOf(1))).divide(BigInteger.valueOf(k));
    }

    public static boolean eq(BigInteger N, BigInteger f){
        BigInteger x1;
        BigInteger x2;
        BigInteger D;
        BigInteger temp;
        temp = N.negate().add(f).subtract((BigInteger.ONE));
        D = temp.pow(2).subtract(N.multiply(BigInteger.valueOf(4)));
        if (D.compareTo(BigInteger.valueOf(0))<0)
            return false;
        if (D.equals(BigInteger.valueOf(0))) {
            if(temp.compareTo(N)==0) {
                return true;
            }
        }
        x1 = temp.subtract(D.sqrt());
        x1 = x1.divide(BigInteger.valueOf(2));
        x2 = temp.add(D.sqrt());
        x2 = x2.divide(BigInteger.valueOf(2));
        return x1.multiply(x2).equals(N);
    }

    public static boolean checkEffectiveness(BigInteger N, int d){
        return N.pow(1 / 4).divide(BigInteger.valueOf(3)).compareTo(BigInteger.valueOf(d)) < 0;
    }

    public static boolean attack(BigInteger n, BigInteger e){
        a = continuousFraction(n, e);
        kd = k_d(a);

        for (int i = 1; i < a.length; i++){
            BigInteger f;
            f = f(e, kd[i][0], kd[i][1]);
            if (eq(n,f)) {
                d = kd[i][1];
                guessD = d;
                break;
            }
        }
        return checkEffectiveness(n, d);
    }

}
