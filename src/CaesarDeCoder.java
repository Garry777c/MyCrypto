import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class CaesarDeCoder implements CaesarCipherTypes {
    //to decode files
        final static private String
                    CODER_TYPE ="CaesarDeCoder class",
                    ENTER_FILE = "Please enter file path and name: ",
                    FILE_NOT_EXIST = "File is not found. Please check file name or path and enter it again: ",
                    ENTER_KEY = "Please enter the key (integer between 1 and 35) for deciphering: ",
                    IS_NOT_NUMERIC =" is not numeric",
                    WRONG_ENTER = "Wrong entering",
                    RE_ENTER_KEY = "Value is out of range. Please enter the key (integer between 1 and 35) for deciphering: ";


    private int key =0;
    private Path sourceFile;
    private char temp;

    @Override
    public void runCaesarCoreType() {
        System.out.println(CODER_TYPE);
        System.out.println(ENTER_FILE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String fileName;
            while ((fileName = reader.readLine()) != null) {
                if (!Files.exists(sourceFile = Path.of(fileName))) {
                    System.out.println(FILE_NOT_EXIST);
                } else break;
            }
            //entering key shifting value
            System.out.println(ENTER_KEY);
            String line;
            try {
                //checking if key is within range
                while ((line = reader.readLine()) != null) {
                    key = Integer.parseInt(line);
                    if (key < 1 || key > 35) {
                        System.out.println(RE_ENTER_KEY);
                    } else break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                //catching NFE exception if string entering
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage() + IS_NOT_NUMERIC);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(WRONG_ENTER);
        }

    }

    @Override
    public void executeCaesarCoreType() {

        //opening FileChannel channel
        try(FileChannel channel = new RandomAccessFile(sourceFile.toFile(), "rw").getChannel()) {

            //reading bytes from channel and writing them to buffer
            ByteBuffer byteBufferReader = ByteBuffer.allocate((int) channel.size());
            channel.read(byteBufferReader);
            byteBufferReader.flip();


            ByteBuffer byteBufferWriter = ByteBuffer.allocate(byteBufferReader.capacity()); //another buffer for writing decoded chars
            while (byteBufferReader.hasRemaining()){
                //char temp;
                char ch = (Character.toLowerCase((char)byteBufferReader.get())); //get bytes from buffer and cast them to char
                if (ch == '\n') {//check if it's the end of line
                    temp = ch;
                } else {
                    for (int i = 0; i < CaesarCipherTypes.Alphabet.length; i++) { //look through the Alphabet array to see amy matches
                        if (CaesarCipherTypes.Alphabet[i] == ch) {
                            if ((i-key) >= 0) {
                                temp = CaesarCipherTypes.Alphabet[i-key];
                            } else {
                                temp = CaesarCipherTypes.Alphabet[(CaesarCipherTypes.Alphabet.length+i)-key];
                            }
                            break;
                        } else {
                            temp=ch;
                        }

                    }
                }
                byteBufferWriter.put((byte) temp); //put decoded chars to buffer

            }
            byteBufferWriter.flip(); //flip buffer to write mode

            channel.truncate(0); //clear original file content
            channel.write(byteBufferWriter); //write new buffer with coded chars to the original file


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
