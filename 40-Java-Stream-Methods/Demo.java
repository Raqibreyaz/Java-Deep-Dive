import java.util.*;
import java.util.stream.*;

public class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1, 4, 11, 13, 11, 34, 21, 13));

        list.stream()
                .filter(x -> x > 10)
                .map(x -> x * 2)
                .sorted()
                .distinct()
                .forEach(System.out::println);

        List<List<Integer>> list2 = List.of(List.of(1, 2), List.of(3, 4));

        // list2.stream()
        // .flatMap(l -> l.stream())
        // .map(x -> x * 2)
        // .forEach(System.out::println);

        Stream.iterate(1, x -> x + 1)
                .limit(10)
                .skip(5)
                .forEach(System.out::println);
    }
}
