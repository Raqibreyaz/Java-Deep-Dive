public class Main {
    public static void main(String[] args) {
        byte b = (byte) (-128 << 1);
        System.out.println(b);

        byte b1 = -128;
        int i = b1;
        System.out.println(i);

        byte b2 = -64;
        int j = 0;
        while (j < 5) {
            System.out.println(b2 << 1);
            System.out.println(Integer.toBinaryString(b2 << 1));
            b2 = (byte) (b2 << 1);
            j++;
        }

        System.out.println(-128 >> 1);
    }
}