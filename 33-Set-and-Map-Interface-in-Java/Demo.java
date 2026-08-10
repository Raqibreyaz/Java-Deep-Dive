import java.util.*;

public class Demo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("raquib");
        set.add("reyaz");

        System.out.println(set.contains("reyaz"));

        Map<Integer, String> map = new HashMap<>();
        map.put(101, "rockstar");
        map.put(102, "raquib");
        map.put(103, "reyaz");

        System.out.println(map.containsKey(101));
        System.out.println(map.get(102));
        System.out.println(map.get(107));
    }
}