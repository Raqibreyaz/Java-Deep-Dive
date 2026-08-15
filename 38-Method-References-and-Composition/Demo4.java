import java.util.function.*;

public class Demo4 {
    public static void main(String[] args) {
        Predicate<Integer> isGreater = x -> x > 100;
        Predicate<Integer> isEven = x -> x % 2 == 0;

        System.out.println(isGreater.and(isEven).test(102));

        // and() --> &&
        // or() --> ||
        // negate() --> !
    }
}
