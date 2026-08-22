import java.io.FileNotFoundException;
import java.io.FileReader;

public class Demo5 {
    public static void main(String[] args) {
        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try (FileReader fr = new FileReader("abc.txt")) {

        } catch (Exception e) {

        }
    }

    private static void readFile() throws FileNotFoundException {
        FileReader fr = new FileReader("abc.txt");
    }
}
