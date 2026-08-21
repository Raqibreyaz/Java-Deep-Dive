import java.util.*;

class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

        list.stream()
                .map(x -> x * 2)
                .forEach(System.out::println);

        System.out.println();

        list.parallelStream()
                .map(x -> x * 2)
                .forEach(System.out::println);
    }
}