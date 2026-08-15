public class Demo {
    public static void main(String[] args) {
        // Calculator c = new Addition();
        Calculator c = (a, b) -> a + b;

        // int sum = c.calculate(12, 19);
        // System.out.println(sum);

        // print(31, 45, c);
        print(31, 45, (a, b) -> a + b);
        print(31, 45, (a, b) -> a * b);
        print(31, 45, (a, b) -> a - b);

        print(31, 45, c);
    }

    static void print(int a, int b, Calculator c) {
        if (c == null)
            return;

        System.out.println(c.calculate(a, b));
    }
}

@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

class Addition implements Calculator {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
