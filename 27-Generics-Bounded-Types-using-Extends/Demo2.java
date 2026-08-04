public class Demo2 {
    public static void main(String[] args) {
        Box b1 = new Box(10);
        Box b2 = new Box("Raquib");
        Box b3 = new Box(true);

        Integer x = (Integer) b1.getValue();
        String s = (String) b2.getValue();
        Boolean b = (Boolean) b3.getValue();

        System.out.println(x);
        System.out.println(s);
        System.out.println(b);
    }
}

class Box {
    private Object value;

    Box(Object value) {
        this.value = value;
    }

    Object getValue() {
        return this.value;
    }
}