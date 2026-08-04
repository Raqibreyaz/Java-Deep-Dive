public class Demo3 {
    public static void main(String[] args) {
        Box<Integer> b1 = new Box<>(10);
        System.out.println(b1.getValue());
    }
}

class Box<T> {
    private T value;

    Box(T value) {
        this.value = value;
    }

    T getValue() {
        return this.value;
    }
}