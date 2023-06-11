package org.example;

import org.example.primaryTest.PrimaryTest;
public class Main {
    public static void main(String[] args) {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec cursus.";
//        String text = "P";
        RSAKey key = new RSAKey(PrimaryTest.MillerRabin, 0.95, 1024);
        try {
            key.generateKey();
        } catch (Exception e) {
            System.out.println("Error in key generation");
            e.printStackTrace();
            System.exit(0);
        }
        RSA rsa = new RSA(key);
        byte[] encrypt = rsa.encrypt(text.getBytes());
        System.out.println("Decrypted: " + rsa.decrypt(encrypt));

        // генерация новой ключевой пары
        try {
            key.generateKey();
            rsa.setKey(key);
        } catch (Exception e) {
            System.out.println("Error in key generation");
            e.printStackTrace();
            System.exit(0);
        }
        encrypt = rsa.encrypt(text.getBytes());
        System.out.println("Decrypted (new key): " + rsa.decrypt(encrypt));

        if (WienerAttack.attack(key.n, key.e)){
            System.out.println("Wiener attack successful!");
            System.out.println("Guessed d = "+ WienerAttack.guessD);
            System.out.println("Fraction collection:");
            for (int i = 0; i < WienerAttack.kd.length; i++){
                int[][]arr = WienerAttack.kd;
                System.out.print(arr[i][0] + "/" + arr[i][1] + " ");
            }
            System.out.println();
        }
        else{
            System.out.println("Wiener attack not successful!");
        }
    }
}