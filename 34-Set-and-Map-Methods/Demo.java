import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // Constructors of HashSet / LinkedHashSet
        
        Set<Integer> set1 = new HashSet<>();

        // using capacity
        Set<Integer> set2 = new HashSet<>(100);

        // using capacity and load-factor
        Set<Integer> set3 = new HashSet<>(100, 0.8f);

        // using another collection
        Set<Integer> set4 = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
}
