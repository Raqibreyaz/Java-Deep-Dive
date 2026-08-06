import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // List<Integer> list = new ArrayList<>();
        // List<Integer> list = new LinkedList<>();
        // Collection<Integer> list = new LinkedList<>();
        // Collection<Integer> list = new HashSet<>();
        // Collection<Integer> list = new ArrayDeque<>();
        Collection<Integer> list = new TreeSet<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            }
    }
}
