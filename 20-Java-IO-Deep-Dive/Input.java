import java.io.IOException;

public class Input {
    public static void main(String[] args) throws IOException {

        // while (true) {
        // int x = System.in.read();

        // if (x == 10)
        // break;

        // System.out.println(x);
        // System.out.println((char) x);
        // }

        byte[] bbuf = new byte[8192];
        int readLen = System.in.read(bbuf, 0, 8192);

        for (int i = 0; i < readLen; i++) {
            char ch = (char) bbuf[i];

            if (ch == '\n')
                break;

            System.out.print((char) bbuf[i]);
        }
        System.out.println();
    }
}
