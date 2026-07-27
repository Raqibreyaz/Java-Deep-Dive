public class ObjectComparison {
    public static void main(String[] args) {
        Integer x1 = 200;
        Integer y1 = 200;
        System.out.println(x1 == y1);

        System.out.println(x1.equals(y1));
        System.out.println(x1.equals(200));

        // loop hole, it gives true instead of false
        System.out.println(Integer.valueOf(20) == Integer.valueOf(20));
    }
}
