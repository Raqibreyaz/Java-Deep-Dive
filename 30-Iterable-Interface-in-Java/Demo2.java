import java.util.Iterator;

public class Demo2 {
    public static void main(String[] args) {
        String[] names = { "Raquib", "Reyaz" };
        NameContainer container = new NameContainer(names);

        Iterator<String> it = container.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // uses iterator behind the scene
        for (String name : container) {
            System.out.println(name);
        }
    }
}

class NameContainer implements Iterable<String> {
    private String[] names;

    NameContainer(String[] names) {
        this.names = names;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < names.length;
            }

            @Override
            public String next() {
                return names[cursor++];
            }
        };
    }
}