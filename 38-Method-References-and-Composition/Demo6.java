import java.util.function.*;

public class Demo6 {
    public static void main(String[] args) {
        Consumer<String> printName = System.out::println;
        Consumer<String> printUpperCase = name -> System.out.println(name.toUpperCase());

        Consumer<String> pipeline = printName.andThen(printUpperCase);

        pipeline.accept("raquib");
    }
}
