import java.io.*;

public class BufferedReaderInput {
    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);

        BufferedReader br = new BufferedReader(isr);

        String value = br.readLine();
        System.out.println(value);

        int val = Integer.parseInt(value);
        System.out.println(val);

        // String name = CustomBufferedReader.read();
        // System.out.println(name);
    }
}
/*
 * 1. Raquib --> input
 * 2. OS Buffer ([82, 97, 113, 117, 105, 98])
 * 3. System.in (InputStream) receives bytes
 * 4. InputStreamReader -> stream of bytes to stream of characters
 * ['R','a','q','u','i','b']
 * 5. BufferedReader -> stream of chars -> buffer(chunk) of chars
 * "Raquib"
 */

class CustomBufferedReader {
    static String read() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);

        char[] cbuf = new char[8192];
        int offset = 0;

        int readLen = isr.read(cbuf, offset, cbuf.length - offset);
        offset += readLen;

        System.out.printf("%d bytes read\n", readLen);

        String str = "";

        for (char c : cbuf) {
            if (c == '\n')
                continue;
            str += c;
        }
        return str;
    }
}