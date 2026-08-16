import java.util.*;

public class Demo2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1, 13, 11, 9, 24));

        // List<Integer> list2 = list.stream()
        // .map(x -> x + 1)
        // .toList();

        // System.out.println(list2);
        // list.remove(0);
        // System.out.println(list2);

        // List<Integer> list3 = list.stream()
        // .map(x -> x + 1)
        // .collect(Collectors.toList());

        // System.out.println(list3);

        // Set<Integer> set = list.stream()
        // .map(x -> x + 1)
        // .collect(Collectors.toSet());

        // System.out.println(set);

        // Integer sum = list.stream()
        // .reduce(0, (a, b) -> a + b);

        // System.out.println(sum);

        // long num = list.stream()
        // .filter(x -> x > 10)
        // .count();

        // Optional<Integer> num = list.stream()
        // .filter(x -> x > 10)
        // .findFirst();

        // Optional<Integer> num = list.stream()
        // .filter(x -> x > 10)
        // .findAny();

        // System.out.println(num.get());

        // boolean b = list.stream()
        // .filter(x -> x > 10)
        // . allMatch(x -> x % 2 == 0);
        // System.out.println(b);

        int sum = list.stream()
                .filter(x -> x > 10)
                .mapToInt(x -> x)
                .sum();
        System.out.println(sum);

        OptionalInt max = list.stream()
                .filter(x -> x > 10)
                .mapToInt(x -> x)
                .max();
        System.out.println(max.getAsInt());
    }
}
