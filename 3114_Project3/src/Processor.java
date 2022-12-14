import java.io.File;
import java.io.IOException;

/**
 * processor class
 * 
 * @author yindrew
 * @version 2022.08.11
 */
public class Processor {

    // field
    private ReplacementSelection rS;
    private String sortedFileName;
    private String tempFileName;
    private int tempName = 0;

    /**
     * constructor
     * 
     * @param fileName
     *            file to be processed
     * @throws IOException
     *             file doesn't exist
     */
    public Processor(String fileName) throws IOException {

        this.sortedFileName = fileName;
        int random = (int)(Math.random() * 100);
        rS = new ReplacementSelection(fileName, "processorTest.bin");
        tempFileName = "processorTest.bin";
        rS.getRuns();

        LinkedList<Integer> temp = rS.runsInfo();

        boolean flag = true;
        while (flag) {
            temp = mergeProcessor(temp, this.tempFileName);

            if (temp.toArray().length == 1) {
                flag = false;
            }
        }

        delRename();

        print(this.sortedFileName);

    }


    /**
     * delete the old file and rename finished one
     */
    public void delRename() {
        File file = new File(this.sortedFileName);

        // delete original fileget
        file.delete();

        File oldName = new File(tempFileName);
        File newName = new File(sortedFileName);

        // rename one run sorted file
        oldName.renameTo(newName);

    }


    /**
     * processes merge
     * 
     * @param runsInfo
     *            linked list of run info
     * @param tFileName
     *            temporary file
     * @return updated run info
     * @throws IOException
     *             if file doesn't exist
     */
    public LinkedList<Integer> mergeProcessor(
        LinkedList<Integer> runsInfo,
        String tFileName)
        throws IOException {

        int runsNum = runsInfo.toArray().length;

        int eightWay = runsNum / 8;
        int remainder = runsNum % 8;
        LinkedList<Integer> ret = new LinkedList<Integer>();

        int[] recordsInfo = runsInfo.toArray();

        MultiWayMerge merge = new MultiWayMerge(tFileName, tempName
            + ".bin", runsInfo);

        // eight way
        for (int i = 0; i < eightWay; i++) {
            merge.merge(8, i * 8);

            int sum = 0;
            for (int x = i * 8; x < i * 8 + 8; x++) {
                sum += recordsInfo[x];
            }
            ret.add(sum);

        }

        // remainder
        merge.merge(remainder, runsNum - remainder);
        int sum = 0;
        for (int i = runsNum - remainder; i < runsNum; i++) {
            sum += recordsInfo[i];
        }
        ret.add(sum);

        this.tempFileName = tempName + ".bin";
        tempName++;
        return ret;

    }


    /**
     * print
     * 
     * @param s
     *            sorted file name
     * @throws IOException
     *             when error occurs
     */
    public void print(String s) throws IOException {
        InputBuffer iB = new InputBuffer(s);

        // get all bytes number
        int byteNum = iB.getAvaliable();
        int blokNum = byteNum / 8192;
        int recordNum = blokNum * 512;

        Record[] printRecord = new Record[recordNum];
        // get all first record in each block
        for (int i = 0; i < blokNum; i++) {
            printRecord[i] = iB.readBlock().getRecords()[0];
        }

        int blockCounter = 0;
        int printCounter = 0;
        // print
        while (blockCounter < blokNum) {
            // Check if new line
            if (printCounter == 5) {
                System.out.println();
                printCounter = 0;
            }

            // Print value
            System.out.print(printRecord[blockCounter].getValue() + " ");

            // Print key
            System.out.print(printRecord[blockCounter].getKey() + " ");

            // Increment block counter
            blockCounter++;
            printCounter++;
        }
    }
}
