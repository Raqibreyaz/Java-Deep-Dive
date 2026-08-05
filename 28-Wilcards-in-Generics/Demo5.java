public class Demo5 {
    public static void main(String[] args) {

    }

    public static <T> void fun(T a, T b) {
    }
    // public static <?> void fun(? a, ? b){} //wont work
}

// wont work
// class Box<?>{
//     ? value;
// }