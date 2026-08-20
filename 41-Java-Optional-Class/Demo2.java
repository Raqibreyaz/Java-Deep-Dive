import java.lang.classfile.ClassFile.Option;
import java.util.Optional;

public class Demo2 {
    public static void main(String[] args) {
        Optional<User> user = getUser();

        // if (user != null) {
        // Address addr = user.get().address;
        // if (addr != null) {
        // if (addr.city != null) {
        // System.out.println(addr.city);
        // }
        // }
        // }

        // Optional<Optional<Address>>
        // user.map(x -> x.address)
        // .map(x -> x.city)
        // .ifPresent(System.out::println);

        // user.flatMap(x -> x.address)
        // .map(x -> x.city)
        // .ifPresent(System.out::println);

        // Optional<String> name = Optional.of("raquib");

        // Optional<String> result = name.filter(x -> x.length() > 10);

        // System.out.println(result.orElse("Empty"));

        Optional<String> name = Optional.of("raquib");
        name.map(x -> x.length())
                .filter(len -> len > 4)
                .ifPresent(System.out::println);
    }

    private static Optional<User> getUser() {
        Address a = new Address();
        a.city = "Varanasi";

        User u = new User();
        u.address = Optional.of(a);

        return Optional.of(u);
    }
}

class User {
    Optional<Address> address;
}

class Address {
    String city;
}