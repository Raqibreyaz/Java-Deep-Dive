import java.util.*;

public class Demo3 {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(101, "Aditya");
        map.put(102, "Rohit");
        map.put(103, "Rohan");

        System.out.println(map.size());
        System.out.println(map.isEmpty());

        System.out.println(map.containsKey(102));
        System.out.println(map.containsValue("Aditya"));

        // returns null for new insertion
        System.out.println(map.put(104, "raquib"));

        // returns old value on updation
        System.out.println(map.put(104, "reyaz"));

        // map.remove(102);
        map.remove(102, "Raquib");

        // Map<Integer, String> map2 = new HashMap<>();
        // map.putAll(map2);

        Set<Integer> set = map.keySet();
        System.out.println(set);

        Collection<String> c = map.values();
        System.out.println(c);

        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        System.out.println(entries);

        System.out.println(map.getOrDefault(108, "Unknown"));

        System.out.println(map);
        map.putIfAbsent(104, "Raquib");
        System.out.println(map);

        // map.replace(101,"Abhay");
        map.replace(101, "Abhay", "Abhijeet");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();

            System.out.printf("%s: %s\n", key, value);
        }

        Map<Integer, String> map2 = Map.of(1, "2", 3, "4", 5, "5");
        map2.put(1,"11");
    }
}
