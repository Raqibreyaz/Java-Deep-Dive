public class AutoboxingUnboxing {
    /*
     * 1. Assignments
     * 2. Method Calls
     * 3. Artithmetic Ops
     */
    public static void main(String[] args) {
        // autoboxing
        int x = 10;
        // Integer y = new Integer(x); //autoboxing
        // Integer y = Integer.valueOf(x); //autoboxing
        Integer y = x; // autoboxing

        System.out.println(x);
        System.out.println(y); // unboxing
        System.out.println(y.intValue()); // unboxing

        // unboxing
        Integer a = 10; // autoboxing
        // int b = a.intValue(); //unboxing
        int b = a; // unboxing

        System.out.println(a); // unboxing
        System.out.println(b);

        printInteger(b); // autoboxing

        Integer x1 = 10;
        Integer x2 = 20;
        int result = x1 + x2 + x1 * x2;
        System.out.println(result);

        // null pointer exception
        try {
            Integer x3 = null;
            int x4 = x3;
            System.out.println(x4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("All things good!");
    }

    static void printInteger(Integer val) {
        System.out.println(val); // unboxing
    }
}