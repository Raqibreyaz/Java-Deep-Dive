public class Demo2 {
    public static void main(String[] args) {
        String s1 = "Ja" + "va";
        String s2 = "Java";
        System.out.println(s1 == s2); // true

        String s3 = "Hello";
        String s4 = s3 + " World";
        String s5 = "Hello World";
        System.out.println(s4 == s5); // false

        String s6 = "Hello";
        String s7 = s6;
        System.out.println(s6 == s7);

        char ch = '𐐀';

        System.out.println(ch);
    }
}
