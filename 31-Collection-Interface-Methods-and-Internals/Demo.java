import java.util.*;

public class Demo {
    public static void main(String[] args) {
        Collection<Integer> c = new HashSet<>();
        c.add(1);
        c.add(2);
        c.add(3);

        int n = c.size();
        System.out.println(n);
        System.out.println(c.isEmpty());
        System.out.println(c.contains(2));

        // for (Object obj : c.toArray()) {
        // System.out.println(obj);
        // }

        // Integer[] arr = c.toArray(new Integer[0]);
        // for (Integer i : arr)
        // System.out.println(i);

        boolean b = c.add(2);
        System.out.println(b);

        System.out.println(c.remove(2));

        Integer[] arr = c.toArray(new Integer[0]);
        for (Integer i : arr)
            System.out.println(i);

        // boolean addAll(Collection<? extends E> c)
        c.addAll(List.of(5, 6, 7, 8, 9));
        System.out.println(c);

        // boolean containsAll(Collection<?> c);
        System.out.println(c.containsAll(List.of(1, 2, 3)));

        // boolean removeAll(Collection<?> c)

        // boolean retainAll() --> Intersection
        System.out.println(c.retainAll(List.of(1,2)));
        System.out.println(c);
    }
}
