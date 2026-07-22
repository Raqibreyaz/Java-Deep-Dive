public class Main {
    public static void main(String[] args) {
        // byte by = 8;
        // int i = by;
        // int i1 = 'c';
        // System.out.println(i + " " + i1);

        // long l = 32;
        // int y = (int) l;
        // System.out.println(y);

        // float f = 8.145f;
        // int z = (int) f;
        // System.out.println(z);

        // short s = 32;
        // s *= 2;
        // s = s * 2;
        // System.out.println(s);
        
        byte b = 42;
        char c = 'a';
        short s = 1024;
        int i = 50000;
        float f = 5.67f;
        double d = .1234;

        double result = (f * b) + (i / c) - (d * s);

        // f * b --> float
        // i / c --> integer
        // d * s --> double

        // float + integer --> float - double --> double

        System.out.println((f * b) + " + " + (i / c) + " - " + (d * s));
        System.out.println("result = " + result);
    }
}