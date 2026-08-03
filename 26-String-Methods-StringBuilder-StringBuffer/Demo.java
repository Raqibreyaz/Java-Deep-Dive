public class Demo {
    public static void main(String[] args) {
        String s1 = new String();
        System.out.println(s1 + " " + s1.length());

        String s2 = new String("Hello");
        System.out.println(s2 + " " + s2.length());

        char[] chs = { 'r', 'a', 'q', 'u', 'i', 'b' };
        String s3 = new String(chs);

        System.out.println(s3 + " " + s3.length());
        chs[0] = 's';
        System.out.println(s3 + " " + s3.length());

        String s4 = new String(chs, 0, 3);
        System.out.println(s4);

        byte[] barr = { 97, 98, 99 };
        String s5 = new String(barr, 0, 3);
        System.out.println(s5);

        StringBuilder sb = new StringBuilder("Hello");
        String s6 = new String(sb);
        System.out.println(s6);

        StringBuffer sb2 = new StringBuffer("Hello");
        String s7 = new String(sb2);
        System.out.println(s7);
    }
}