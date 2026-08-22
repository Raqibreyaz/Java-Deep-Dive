public class Demo3 {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;

        System.out.println("Step 1");

        // try {
        // System.out.println(a / b); // illegal --> new ArithmeticException("/ by
        // zero")
        // } catch (ArithmeticException e) {
        // System.out.println("Division by 0 not allowed");
        // System.out.println(e.getMessage());
        // e.printStackTrace();
        // }

        try {
            System.out.println(a / b); // illegal --> new ArithmeticException("/ by zero")
        } finally {
            System.out.println("Division by 0 not allowed");
        }

        System.out.println("Step 2");
    }
}
