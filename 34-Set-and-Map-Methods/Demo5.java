import java.util.*;

public class Demo5 {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();

        map.put(101, "raquib");
        map.put(102, "reyaz");
        map.put(103, "imran");

        System.out.println(map.firstEntry());
        System.out.println(map.lastEntry());

        System.out.println(map.headMap(103));
        System.out.println(map.headMap(102));

        System.out.println(map.subMap(101, 103));

        System.out.println(map.lowerKey(103));
        
        System.out.println(map.higherEntry(103));
        
        System.out.println(map.descendingMap());
    }
}
