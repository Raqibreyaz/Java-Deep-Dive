import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Demo3 {
    public static void main(String[] args) {
        // List<Integer> list = new ArrayList<>(List.of(1, 13, 11, 9, 24));

        // Map<Boolean, List<Integer>> map = list.stream()
        // .collect(Collectors.partitioningBy(x -> x % 2 == 0));
        // System.out.println(map);

        List<String> list = new ArrayList<>(List.of("AA", "BBB", "DD", "CCCC",
                "EEE"));

        // Map<Integer, String> map = list.stream()
        // .collect(Collectors.toMap(str -> str.length(), str -> str));

        // Map<Integer, List<String>> map = list.stream()
        // .collect(Collectors.groupingBy(str -> str.length()));

        Map<Integer, List<String>> map = list.stream()
                .collect(Collectors.groupingBy(str -> str.length(),
                        Collectors.mapping(x -> x.toLowerCase(), Collectors.toList())));
        System.out.println(map);

        String result = list.stream()
                .collect(Collectors.joining("-"));
        System.out.println(result);
    }
}
