import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class CaesarCoder implements CaesarCipherTypes {
    //to code files
    final static private String
                    CODER_TYPE = "CaesarCoder class",
                    ENTER_FILE = "Please enter file path and name: ",
                    FILE_NOT_EXIST = "File is not found. Please check file name or path and enter it again: ",
                    ENTER_CODE = "Please enter the code (integer between 1 and 35) for ciphering: ",
                    IS_NOT_NUMERIC =" is not numeric",
                    WRONG_ENTER = "Wrong entering",
                    RE_ENTER_CODE = "Value is out of range. Please enter the code (integer between 1 and 35) for ciphering: ";


    private int code=0;
    private Path sourceFile;
    private Path destFile;
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
            //entering code shifting value
            System.out.println(ENTER_CODE);
            String line;
            try {
                //checking if code is within range
                while ((line = reader.readLine()) != null) {
                    code = Integer.parseInt(line);
                    if (code < 1 || code > 35) {
                        System.out.println(RE_ENTER_CODE);
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

        try(
                FileChannel channel = new RandomAccessFile(sourceFile.toFile(), "rw").getChannel()) {

            //reading bytes from channel and writing them to buffer
            ByteBuffer byteBufferReader = ByteBuffer.allocate((int) channel.size());

            channel.read(byteBufferReader);
            byteBufferReader.flip();


            ByteBuffer byteBufferWriter = ByteBuffer.allocate(byteBufferReader.capacity());//another buffer for writing coded chars
            while (byteBufferReader.hasRemaining()){

                char ch = (Character.toLowerCase((char)byteBufferReader.get())); //get bytes from buffer and cast them to char
                if (ch == '\n') {//check if it's the end of line
                    temp = ch;
                } else {
                    for (int i = 0; i < CaesarCipherTypes.Alphabet.length; i++) { //look through the Alphabet array to see amy matches
                        if (CaesarCipherTypes.Alphabet[i] == ch) {
                            if ((code + i) <= 36) {
                                temp = CaesarCipherTypes.Alphabet[code + i];
                            } else {
                                temp = CaesarCipherTypes.Alphabet[(code + i) % CaesarCipherTypes.Alphabet.length];
                            }
                            break;
                        } else {
                            temp = ch;
                        }

                    }
                }
                byteBufferWriter.put((byte) temp); //put coded chars to buffer

            }
            byteBufferWriter.flip(); //flip buffer to write mode

            channel.truncate(0); //clear original file content
            channel.write(byteBufferWriter); //write new buffer with coded chars to the original file



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
