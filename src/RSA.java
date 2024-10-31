import java.util.*;
public class RSA {

    int p, q, nkey, ekey;
    int PQ;

    public List<Integer> encrypt(String message, int nkey, int ekey) {
        List<String> codedmsg_blocked = blockSplit(wordtonum(message), String.valueOf(nkey).length());
        List<Integer> codemsg_finished = new ArrayList<>();

        for (String block : codedmsg_blocked) {
            int encryptedBlock = BME(Integer.parseInt(block), ekey, nkey);
            codemsg_finished.add(encryptedBlock);
        }

        return codemsg_finished;
    }

    public String wordtonum(String message) {
        StringBuilder numMessage = new StringBuilder();
        for (char c : message.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                int position = c - 'a';
                numMessage.append(String.format("%02d", position));
            } else {
                numMessage.append(c);
            }
        }
        return numMessage.toString();
    }

    public String numtoword(String numMessage) {
        StringBuilder originalMessage = new StringBuilder();
        int length = numMessage.length();

        if (length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number format");
        }

        for (int i = 0; i < length; i += 2) {
            String digits = numMessage.substring(i, i + 2);
            int position = Integer.parseInt(digits);
            char letter = (char) ('a' + position);
            originalMessage.append(letter);
        }

        return originalMessage.toString();
    }

    public List<String> blockSplit(String numMessage, int N) {
        List<String> blocks = new ArrayList<>();
        int blockSize = 2 * (N / 2);

        if (numMessage.length() % blockSize != 0) {
            int paddingLength = blockSize - (numMessage.length() % blockSize);
            numMessage += "0".repeat(paddingLength);
        }

        for (int i = 0; i < numMessage.length(); i += blockSize) {
            blocks.add(numMessage.substring(i, i + blockSize));
        }

        return blocks;
    }

    public int BME(int M, int e, int n) {
        String binary = Integer.toBinaryString(e);
        int x = 1;
        int p = M % n;

        for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                x = (x * p) % n;
            }
            p = (p * p) % n;
        }

        return x;
    }

    public boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void primeFactorization() {
        for (int i = 2; i <= Math.sqrt(nkey); i++) {
            if (nkey % i == 0) {
                int quotient = nkey / i;
                if (isPrime(i) && isPrime(quotient)) {
                    p = i;
                    q = quotient;
                    PQ = (p - 1) * (q - 1);
                    return;
                }
            }
        }
    }

    public String decrypt(int[] codeArray) {
        primeFactorization();
        int dKey = inverseMod(this.ekey, PQ);
        StringBuilder decryptedMsg = new StringBuilder();

        for (int code : codeArray) {
            int decryptedBlock = BME(code, dKey, this.nkey);
            String temp3 = String.format("%0" + String.valueOf(nkey).length() + "d", decryptedBlock);
            decryptedMsg.append(temp3);
        }

        String decryptedString = decryptedMsg.toString();

        // Remove padding
        while (decryptedString.endsWith("0")) {
            decryptedString = decryptedString.substring(0, decryptedString.length() - 1);
        }

        return numtoword(decryptedString);
    }

    private int inverseMod(int e, int pq) {
        int t = 0, newT = 1;
        int r = pq, newR = e;

        while (newR != 0) {
            int quotient = r / newR;

            int tempT = t;
            t = newT;
            newT = tempT - quotient * newT;

            int tempR = r;
            r = newR;
            newR = tempR - quotient * newR;
        }

        if (r > 1) {
            throw new ArithmeticException("e is not invertible");
        }
        if (t < 0) {
            t += pq;
        }

        return t;
    }
}