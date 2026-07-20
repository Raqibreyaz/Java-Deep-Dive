public class Main {
    public static void main(String[] args) {
        double dd = 1.1414D;
        System.out.println(dd);
        System.out.println(Math.sqrt(2D));

        System.out.println("raquib\0reyaz");

        String ch = "𪣻";
        System.out.println(ch);

        Boolean x = false; // can't be 0
        if (x) {
        } // strictly requires true/false

        byte b1 = 12;
        byte b2 = 0b101;
        byte b3 = 07;
        byte b4 = 0x2B;

        long l = 123_456_789;

        float f = 10.5_4f;

        double d = 6.02___2e23;

        char i = (char) 0x25903;
        System.out.println(i);

        char c1 = 'औ';
        char c2 = (char)0x122AB; //couldn't store the char properly

        System.out.println(c2);
    }
}
