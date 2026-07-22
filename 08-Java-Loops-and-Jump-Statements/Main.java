public class Main {
    public static void main(String[] args) {
        first: {
            second: {
                break first;
            }
        }

        outer: for (int i = 0; i < 10; i++) {
            inner: for (int j = 0; j < 10; j++) {
                if (i % 2 == 0)
                    break outer;
            }
        }
    }
}
