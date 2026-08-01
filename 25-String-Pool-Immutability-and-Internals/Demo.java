public class Demo {
    public static void main(String[] args) {
        char[] chs = { 'r', 'a', 'q', 'u', 'i', 'b' };
        System.out.println(chs);

        String s1 = "Hello";
        String s2 = "Hello";
        System.out.println(s1 == s2); //true

        String s3 = new String("Hello");
        String s4 = new String("Hello");
        System.out.println(s3 == s4); //false
    }
}