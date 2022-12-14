/**
 * A array of records, specifically 512
 * 
 * @author yindrew
 * @version 2022.08.10
 */
public class Block {
    
    //field
    private Record[] recordArr = new Record[512];

    /**
     * constructor
     * 
     * @param arr
     *            the byte array we are converting
     */
    public Block(byte[] arr) {
        for (int x = 0; x < (arr.length / 16); x++) {
            byte[] sliced = slice(arr, x * 16, x * 16 + 16);
            Record record = new Record(sliced);
            recordArr[x] = record;
        }

    }


    /**
     * slices an array into smaller pieces
     * 
     * @param arr
     *            the array
     * @param start
     *            the starting index
     * @param end
     *            the ending index
     * @return array
     *            the sliced array
     */
    private byte[] slice(byte[] arr, int start, int end) {
        byte[] slice = new byte[end - start];

        for (int i = 0; i < slice.length; i++) {
            slice[i] = arr[start + i];
        }

        return slice;
    }


    /**
     * getter method for the record array
     * 
     * @return the records
     *            records
     */
    public Record[] getRecords() {
        return recordArr;
    }


    /**
     * toString method
     * 
     * @return String
     *          the array of records
     */
    public String toString() {
        String sum = "";
        for (int x = 0; x < 512; x++) {
            sum = sum + recordArr[x].toString() + " ";

        }
        return sum;
    }

}
