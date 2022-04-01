import java.util.Scanner;

public class Runner {
    //start point

    //set String variables
    final static private String
            WELCOME =  "Welcome to Caesar Crypto!",
            MAKE_CHOICE =  "Please enter number of type of ciphering (from 1 to 4) you would go :",
            CRYPTO_TYPES = """
                    1 - Coding file...
                    2 - Decoding file...
                    3 - Using "Brute Force" method to decrypt file...
                    4 - Using "Static Analysis" method to decrypt file...
                    """,
            PICK_TYPE = "Please enter correct number from 1 to 4: ";



    public static void main(String[] args) {

        System.out.println(WELCOME);
        System.out.println(MAKE_CHOICE);
        System.out.println(CRYPTO_TYPES);

            //new Fabric variable
            TypesOfCode typesOfCode = null;

            //requesting user what method they want to use
            Scanner scanner = new Scanner(System.in);
            int codeNumber = 0;
            while(scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    //this block is for integers only
                    codeNumber = scanner.nextInt();
                    //if customer entered wrong int app is asking to enter correct number
                    if (codeNumber != 1 && codeNumber != 2 && codeNumber != 3 && codeNumber != 4) {
                        System.out.println(PICK_TYPE);
                    } else break;
                } else {
                    //if customer entered any string, app is showing the wrong entering and is asking to enter correct number
                    String x = scanner.nextLine();
                    System.out.format("You entered \"%s\"."+PICK_TYPE, x);
                }
            }

                //depending on entered value, a new object on enum is created
                switch (codeNumber) {
                        case 1 -> typesOfCode = TypesOfCode.CODER;
                        case 2 -> typesOfCode = TypesOfCode.DECODER;
                        case 3 -> typesOfCode = TypesOfCode.BRUTE_FORCE;
                        case 4 -> typesOfCode = TypesOfCode.STATIC_ANALYSIS;

                }


                //New interface variable is created and depending on type of enum, a new object is created using Factory method
                CaesarCipherTypes currentCryptoType = new CryptoFactory().getTypeOfCrypto(typesOfCode);
                currentCryptoType.runCaesarCoreType();
                currentCryptoType.executeCaesarCoreType();


                //all logic for ciphering will be inside the active objects

    }


}
