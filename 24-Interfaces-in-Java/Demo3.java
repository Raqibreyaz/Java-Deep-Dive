public class Demo3 {
    public static void main(String[] args) {
        Random r = new Random();
        r.fun();

        System.out.println(MathConstant.PI_VALUE);
    }
}

interface MathConstant {
    double PI_VALUE = 3.14;
    int VALUE = 10;
}

class Random implements MathConstant {
    void fun() {
        System.out.println(PI_VALUE);
    }
}