public class Demo2 {
    public static void main(String[] args) {

        // outer catch will handle the exception
        try {
            System.out.println("Outer try starts...");

            try {

                System.out.println("Inner try starts...");
                System.out.println(5 / 0);
                System.out.println("Inner try ends...");

            } catch (NullPointerException e) {
                System.out.println("Nulls are not allowed: Inner");
            }

            System.out.println("Outer try ends...");
        } catch (Exception e) {
            System.out.println("divide by zero is not allowed: Outer");
        }
    }
}
