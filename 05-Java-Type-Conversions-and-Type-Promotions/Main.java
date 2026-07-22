public class Main {
    public static void main(String[] args) {
        byte by = 8;
        int i = by;
        int i1 = 'c';
        System.out.println(i + " " + i1);

        long l = 32;
        int y = (int) l;
        System.out.println(y);

        float f = 8.145f;
        int z = (int) f;
        System.out.println(z);

        short s = 32;
        s *= 2;
        s = s * 2;
        System.out.println(s);
    }
}
