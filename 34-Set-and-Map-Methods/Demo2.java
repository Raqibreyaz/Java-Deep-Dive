import java.util.*;

public class Demo2 {
    public static void main(String[] args) {
        // TreeSet<Integer> set = new TreeSet<>(List.of(1, 2, 3, 4, 5, 6));

        TreeSet<Integer> set = new TreeSet<>();

        set.add(80);
        set.add(23);
        set.add(10);
        set.add(90);
        set.add(50);

        // SortedSet Interface ---> O(log N)
        // System.out.println(set.first());
        // System.out.println(set.last());

        // toElement is exclusive by default
        System.out.println(set.headSet(90,true));

        // fromElement is inclusive by default
        System.out.println(set.tailSet(23,false));

        // Navigable Set

        // largest number < 80
        System.out.println(set.lower(80));
        
        // largest number <= 80
        System.out.println(set.floor(80));

        // smallest number > 80
        System.out.println(set.higher(80));
        
        // smallest number >= 80
        System.out.println(set.ceiling(80));
        
        // System.out.println(set);
        // System.out.println(set.pollFirst());
        // System.out.println(set.pollLast());
        // System.out.println(set);

        System.out.println(set.descendingSet());
    }
}
