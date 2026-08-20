import java.util.Optional;

public class Demo {
    public static void main(String[] args) {
        Optional<String> name = getName();

        // if(name.isPresent())
        // System.out.println(name.get());

        name.ifPresent(System.out::println);

        System.out.println(name.orElse("unknown"));

        System.out.println(name.orElseGet(() -> "unknown"));

        // System.out.println(name.orElseThrow());

        name.ifPresentOrElse(System.out::println, () -> System.out.println("Unknown"));
    }

    static Optional<String> getName() {
        // return Optional.of("raquib");
        // return Optional.ofNullable("raquib");
        return Optional.ofNullable(null);
    }
}
