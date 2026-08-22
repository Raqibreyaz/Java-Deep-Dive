public class Demo4 {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;

        try {
            methodA(a, b);
        } catch (Exception e) {
            System.out.println("Division by 0 not allowed");
        }
    }

    private static void methodA(int a, int b) {
        methodB(a, b);
    }

    private static void methodB(int a, int b) {
        System.out.println(a / b);
    }
}