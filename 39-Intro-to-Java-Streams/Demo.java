import java.util.*;
import java.util.stream.*;

public class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(12, 5, 7, 14, 23));

        Stream<Integer> s = list.stream();

        s.filter(x -> x > 10)
                .map(x -> x * 2)
                .forEach(System.out::println);
    }
}