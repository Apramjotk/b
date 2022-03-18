public class Encryptor {
    /**
     * A two-dimensional array of single-character strings, instantiated in the constructor
     */
    private String[][] letterBlock;

    /**
     * The number of rows of letterBlock, set by the constructor
     */
    private int numRows;

    /**
     * The number of columns of letterBlock, set by the constructor
     */
    private int numCols;

    /**
     * Constructor
     */
    public Encryptor(int r, int c) {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }
    public Encryptor(String[][] message)
    {
        this.letterBlock = message;
    }

    public String[][] getLetterBlock() {
        return letterBlock;
    }

    /**
     * Places a string into letterBlock in row-major order.
     *
     * @param str the string to be processed
     *            <p>
     *            Postcondition:
     *            if str.length() < numRows * numCols, "A" in each unfilled cell
     *            if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str) {
        int i = 0;
        for (int e = 0; e < letterBlock.length; e++) {
            for (int d = 0; d < letterBlock[0].length; d++) {
                if (i < str.length()) {
                    letterBlock[e][d] = str.substring(i, i + 1);
                    i++;
                } else {
                    letterBlock[e][d] = "A";
                }

            }
        }
    }

    /**
     * Extracts encrypted string from letterBlock in column-major order.
     * <p>
     * Precondition: letterBlock has been filled
     *
     * @return the encrypted string from letterBlock
     */
    public String encryptBlock() {
        String g = "";
        for (int e = 0; e < letterBlock[0].length; e++) {
            for (int d = 0; d < letterBlock.length; d++) {
                g += letterBlock[d][e];
            }
        }
        return g;
    }

    /**
     * Encrypts a message.
     *
     * @param message the string to be encrypted
     * @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        String d = "";
        while (!message.equals("")) {
            if (numCols * numRows < message.length()) {
                String c = message.substring(0, numRows * numCols);
                message = message.substring(numRows * numCols);
                fillBlock(c);
                d += encryptBlock();
            } else {
                fillBlock(message);
                d += encryptBlock();
                message = ""; // final update
            }
        }
        return d;
    }

    /**
     * Decrypts an encrypted message. All filler 'A's that may have been
     * added during encryption will be removed, so this assumes that the
     * original message (BEFORE it was encrypted) did NOT end in a capital A!
     * <p>
     * NOTE! When you are decrypting an encrypted message,
     * be sure that you have initialized your Encryptor object
     * with the same row/column used to encrypted the message! (i.e.
     * the “encryption key” that is necessary for successful decryption)
     * This is outlined in the precondition below.
     * <p>
     * Precondition: the Encryptor object being used for decryption has been
     * initialized with the same number of rows and columns
     * as was used for the Encryptor object used for encryption.
     *
     * @param encryptedMessage the encrypted message to decrypt
     * @return the decrypted, original message (which had been encrypted)
     * <p>
     * TIP: You are encouraged to create other helper methods as you see fit
     * (e.g. a method to decrypt each section of the decrypted message,
     * similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage) {
        String decr = "";
        String d= "";
        int u=0;
        String[][] mess = new String[numRows][numCols];
        int x = numRows * numCols;
        for (int i = 0; i < encryptedMessage.length(); i += x)
        {
            int k=  i + x;
            String b = encryptedMessage.substring(i, k);
            int p = 0;
            for (int z = 0; z < numCols; z++)
            {
                for (int y= 0; y< numRows; y++)
                {
                    mess[y][z] = b.substring(p, p + 1);
                    p++;
                }
            }

            for (int s = 0; s < numRows; s++)
            {
                for (int t = 0; t < numCols; t++)
                {
                    decr+= mess[s][t];
                }
            }
             int c= decr.length();

            while (decr.length()>u)
            {
                String sub = decr.substring(u, x);
                int index= sub.length();

                while (sub.substring(index - 1, index).equals("A") ) {

                        index--;

                }
                d+= sub.substring(u, index);
                decr = decr.substring(x, c);
            }
        }
        return d;
}
    public void rowShifted(int keyRight){
        String decr = "";
        String d= "";
         if (keyRight>0){
             for (int r=0; r< letterBlock.length; r++){
                 for(int move= 0; move< keyRight; move++) {
                     int x = letterBlock[r].length - 1;
                     decr = letterBlock[r][x];
                     for (int c= x; c>0; c--){
                         letterBlock[r][c]= letterBlock[r][c-1];
                     }
                     letterBlock[r][0]= decr;
                 }
             }
         }
         else{
             int key = Math.abs(keyRight);
             for (int r=0; r< letterBlock.length; r++) {
                 for(int move= 0; move< key; move++) {
                     int x = letterBlock[r].length - 1;
                      decr= letterBlock[r][0] ;
                     for (int c= 0; c<x; c++){
                         letterBlock[r][c]= letterBlock[r][c+1];
                     }
                     letterBlock[r][x]= decr;

                 }
             }
             }

        System.out.println("The shift is "+ keyRight+ " rows");

}
    public void colShifted(int keyRight){
        String decr = "";
        String d= "";
        if (keyRight>0){
            for (int c=0; c< letterBlock[0].length; c++){
                for(int move= 0; move< keyRight; move++) {
                    int x = letterBlock.length-1;
                    decr = letterBlock[x][c];
                    for (int r= x; r>0; r--){
                        letterBlock[r][c]= letterBlock[r-1][c];
                    }
                    letterBlock[0][c]= decr;
                }
            }
        }
        else{
            int key = Math.abs(keyRight);
            for (int c=0; c< letterBlock[0].length; c++) {
                for(int move= 0; move< key; move++) {
                    int x = letterBlock.length - 1;
                    decr= letterBlock[0][c] ;
                    for (int r= 0; r<x; r++){
                        letterBlock[r][c]= letterBlock[r+1][c];
                    }
                    letterBlock[x][c]= decr;


                }
            }
        }
        System.out.println("The shift is "+ keyRight+ " columns");


    }


}
