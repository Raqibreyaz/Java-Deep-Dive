public class Args {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        
        System.out.println(System.getenv("HOME"));
    }
}
