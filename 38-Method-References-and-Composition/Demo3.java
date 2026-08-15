import java.util.function.*;
import java.util.*;

public class Demo3 {
    public static void main(String[] args) {
        // (x + 2) * 3 --> x+2, x*3
        Function<Integer, Integer> equation = x -> (x + 2) * 3;

        Function<Integer, Integer> add2 = x -> x + 2;
        Function<Integer, Integer> multiply3 = x -> x * 3;
        Function<Integer, Integer> divide2 = x -> x / 3;

        int a = add2.apply(10);
        int b = multiply3.apply(a);

        int ans = add2.andThen(multiply3).apply(2);
        int ans2 = add2.andThen(multiply3).andThen(divide2).apply(2);

        System.out.println(ans);
        System.out.println(ans2);
    }
}
