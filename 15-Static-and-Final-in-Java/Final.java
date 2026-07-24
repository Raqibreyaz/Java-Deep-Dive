public class Final {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(Random.PI);

        final int x = 4;
        System.out.println(x);

        final int y;
        y = 6;
        System.out.println(y);
    }
}

class Random {
    static final double PI = 3.14;
}