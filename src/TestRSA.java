import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class TestRSA {
    public static void main(String[] args) {
        RSA code = new RSA();
        Scanner input = new Scanner(System.in);
        int userInput;

        System.out.println("To procceed, please enter in the following key values: ");

        System.out.println("Enter key value for n");
        code.nkey = input.nextInt();

        System.out.println("Enter key value for e");
        code.ekey = input.nextInt();

        do {
            System.out.println("---------------------------------");
            System.out.println("Welcome. Would you like to Encrypt or Decrypt a message?");
            System.out.println("Key inputed: " + "(" + code.nkey + " , " + code.ekey + ")" );
            System.out.println("---------------------------------");
            System.out.println("1. Encrypt a message");
            System.out.println("2. Decrypt a message");
            System.out.println("3. Change key codes");
            System.out.println("0. Exit program");
            System.out.println("---------------------------------");

            userInput = input.nextInt();

            switch(userInput){
                case 1:
                    System.out.println("Enter the message you want to encrypt: ");
                    String myMessage = input.next();

                    List<Integer> encryptedMsg = code.encrypt(myMessage, code.nkey, code.ekey);

                    System.out.println("Your message '" + myMessage + "' was encrypted into: ");
                    System.out.println(encryptedMsg);
                    System.out.println();
                    System.out.println();
                    break;


                case 2:
                    System.out.println("How many blocks is your message?");
                    int[] codeArray = new int[input.nextInt()];

                    for(int i = 0; i < codeArray.length; i++){
                        System.out.println("Enter the code from block " + (i + 1) );
                        System.out.println("ENSURE THAT DIGITS 0-9 ARE PADDED");
                        codeArray[i] = input.nextInt();
                    }

                    String decodedMessage = code.decrypt(codeArray);

                    System.out.println("Decrypted message was encrypted into: ");
                    System.out.println(decodedMessage);
                    System.out.println();
                    System.out.println();

                    break;

                case 3:
                    System.out.println("Enter key value for n");
                    code.nkey = input.nextInt();

                    System.out.println("Enter key value for e");
                    code.ekey = input.nextInt();

                    System.out.println();
                    System.out.println();

                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;


                default:
                    System.out.println("Invalid choice. Enter a valid option listed.");
                    break;

            }

        } while(userInput != 0);

        input.close();




    }
}