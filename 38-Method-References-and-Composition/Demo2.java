import java.util.*;
import java.util.function.*;

public class Demo2 {
    public static void main(String[] args) {
        Function<Integer, Integer> square = x -> x * x;
        // System.out.println(square.apply(5));

        Consumer<Integer> print = x -> System.out.println(x);
        // print.accept(23);

        Supplier<Double> randomValue = () -> Math.random();
        // System.out.println(randomValue.get());

        Predicate<Integer> isEven = x -> x % 2 == 0;
        // System.out.println(isEven.test(23));

        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));

        // list.forEach((val) -> System.out.println(val));
        // list.forEach(System.out::println);
        list.forEach(new Consume());
    }
}

class Consume implements Consumer<Integer> {
    @Override
    public void accept(Integer t) {
        System.out.println(t);
    }
}