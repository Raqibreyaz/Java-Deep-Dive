public class Demo6 {
    public static void main(String[] args) {
        Box<Integer> b1 = new Box<>(20);
        b1.printDouble();
    }
}

class Box<T extends Number> {
    T value;

    Box(T value){
        this.value = value;
    }

    public void printDouble() {
        System.out.println(value.doubleValue());
    }
}