import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaesarBruteForceDeCoder implements CaesarCipherTypes{
    //to decode files using "Brute-Force" cipher

    final static private String
            CODER_TYPE ="Caesar Brute-Force Class",
            ENTER_FILE = "Please enter file path and name: ",
            FILE_NOT_EXIST = "File is not found. Please check file name or path and enter it again: ",
            WRONG_ENTER = "Wrong entering";

    private Path sourceFile;
    private Path decodedFile;
    private Path destFile;
    HashMap <Integer, Integer> keyMap = new HashMap<>();
    int count;
    char temp;



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
        ArrayList <String> arrayList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(the|to|be)\\b"); //pattern to look words "the" "to" "be"

        try {
            decodedFile = Files.createTempFile("temp","txt");//temp text file created
            destFile = Files.createFile(Path.of("destFile.txt"));
        } catch (IOException e) {
            System.out.println("Can' create temp");
        }


        for(var pair:keyMap.entrySet()) {
            Integer key = pair.getKey();
                //go to the loop for each key-value using key as "key" fo decoding
            count = 0; //zero counter for each key

            //opening FileChannel channels for reading and writing

            try(FileChannel channel = new RandomAccessFile(sourceFile.toFile(), "r").getChannel();
                FileChannel channelWrite = new RandomAccessFile(decodedFile.toFile(), "rw").getChannel()) {

                //reading bytes from channel and writing them to buffer
                ByteBuffer byteBufferReader = ByteBuffer.allocate((int) channel.size());
                //if we have a big text file it is reasonable to analyze just a part of text,
                // it can be done by set ByteBuffer size to particular value, for instance 1024 or 2048,
                // it is more than enough for analyzing
                channel.read(byteBufferReader);
                byteBufferReader.flip();


                ByteBuffer byteBufferWriter = ByteBuffer.allocate(byteBufferReader.capacity()); //another buffer for writing decoded chars
                while (byteBufferReader.hasRemaining()){

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

                channelWrite.write(byteBufferWriter); //write new buffer with coded chars to the new file

            } catch (IOException e) {
                e.printStackTrace();
            }
                //look for any common words or string inside temp text and count how many for this key
            try (BufferedReader reader = new BufferedReader(new FileReader(decodedFile.toFile()))) {
                while(reader.ready()) {
                    arrayList.add(reader.readLine());//move string line to arraylist
                }
            } catch (IOException e) {
                System.out.println("File not found or doesn't exist");
            }

            for (String str : arrayList) { //read every line and check if any words from patters are there
                Matcher m = pattern.matcher(str);
                while (m.find()) {
                    count ++; //summarising all counts
                }
            }
            pair.setValue(count); //set int value (counts) for this particular key
            arrayList.clear();
        } //end foreach loop


      int keyFinal = Collections.max(keyMap.entrySet(), Map.Entry.comparingByValue()).getKey(); //get the key with max value (counts)
        System.out.println("Found key value: "+ keyFinal);

        try(FileChannel channel = new RandomAccessFile(sourceFile.toFile(), "r").getChannel();
            FileChannel channelWrite = new RandomAccessFile(destFile.toFile(), "rw").getChannel()) {

            //reading bytes from channel and writing them to buffer
            ByteBuffer byteBufferReader = ByteBuffer.allocate((int) channel.size());
            channel.read(byteBufferReader);
            byteBufferReader.flip();


            ByteBuffer byteBufferWriter = ByteBuffer.allocate(byteBufferReader.capacity()); //another buffer for writing decoded chars
            while (byteBufferReader.hasRemaining()){

                char ch = (Character.toLowerCase((char)byteBufferReader.get())); //get bytes from buffer and cast them to char
                if (ch == '\n') {//check if it's the end of line
                    temp = ch;
                } else {
                    for (int i = 0; i < CaesarCipherTypes.Alphabet.length; i++) { //look through the Alphabet array to see amy matches
                        if (CaesarCipherTypes.Alphabet[i] == ch) {
                            if ((i-keyFinal) >= 0) {
                                temp = CaesarCipherTypes.Alphabet[i-keyFinal];
                            } else {
                                temp = CaesarCipherTypes.Alphabet[(CaesarCipherTypes.Alphabet.length+i)-keyFinal];
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

            channelWrite.write(byteBufferWriter); //write new buffer with coded chars to the new file

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
