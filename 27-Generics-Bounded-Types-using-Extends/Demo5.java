public class Demo5 {
    public static void main(String[] args) {
        System.out.println(getResult(4));
        System.out.println(getResult("Raquib"));

        printPair("raquib", "reyaz");
    }

    public static <T> T getResult(T x) {
        return x;
    }

    public static <T, U> void printPair(T x, U y) {
        System.out.println(x + " " + y);
    }
}
