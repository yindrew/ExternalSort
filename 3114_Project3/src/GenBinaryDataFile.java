
/**
 * Generate a data file. The size is a multiple of 8192 bytes.
 * Each record is one long and one double.
 */

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GenBinaryDataFile {

    static final int RECS_PER_BLOCK = 512;
    static final int BYTES_PER_RECORD = Long.BYTES + Double.BYTES; // should be
                                                                   // 8 + 8

    public static void generateRandom(String filename, int numBlocks)
        throws IOException {
        Random randGen = new Random();
        int numRecords = numBlocks * RECS_PER_BLOCK;
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(filename)));

        long value;
        double key;
        for (int i = 0; i < numRecords; i++) {
            // note that the value comes first, the key comes second
            value = randGen.nextLong();
            dos.writeLong(value);
            key = randGen.nextDouble();
            dos.writeDouble(key);
        }
        dos.flush();
        dos.close();
    }

    // Want to read a file? Well look above and think about changing
    // 'Output' to 'Input'

}
