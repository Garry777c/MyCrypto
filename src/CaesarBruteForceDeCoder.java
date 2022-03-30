import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class CaesarBruteForceDeCoder implements CaesarCipherTypes{
    //to decode files using "Brute-Force" cipher

    final static private String
            CODER_TYPE ="Caesar Brute-Force Class",
            ENTER_FILE = "Please enter file path and name: ",
            FILE_NOT_EXIST = "File is not found. Please check file name or path and enter it again: ",
            ENTER_KEY = "Please enter the key (integer between 1 and 35) for deciphering: ",
            IS_NOT_NUMERIC =" is not numeric",
            WRONG_ENTER = "Wrong entering",
            RE_ENTER_KEY = "Value is out of range. Please enter the key (integer between 1 and 35) for deciphering: ";

    private Path sourceFile;
    HashMap <Integer, Integer> keyMap = new HashMap<>();
    int count;

    @Override
    public void runCaesarCoreType() {
        System.out.println(CODER_TYPE);
        System.out.println(ENTER_FILE);

        //getting file name
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String fileName;
            while ((fileName = reader.readLine()) != null) {
                if (!Files.exists(sourceFile = Path.of(fileName))) {
                    System.out.println(FILE_NOT_EXIST);
                } else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(WRONG_ENTER);
        }

        //filling hash map with all possible key value 1 to 35
        for (int i = 1; i <=35; i++) {
            keyMap.put(i, 0);
        }

    }

    @Override
    public void executeCaesarCoreType() {

    }
}
