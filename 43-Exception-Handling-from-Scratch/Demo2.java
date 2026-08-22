public class Demo2 {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;

        methodA(a, b);
    }

    private static void methodA(int a, int b){
        methodB(a, b);
    }
    private static void methodB(int a, int b){
        System.out.println(a / b);
    }
}

/*
Exception in thread "main" java.lang.ArithmeticException: / by zero
        at Demo2.methodB(Demo2.java:13)
        at Demo2.methodA(Demo2.java:10)
        at Demo2.main(Demo2.java:6)
*/