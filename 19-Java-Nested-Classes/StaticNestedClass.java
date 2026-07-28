public class StaticNestedClass {
    public static void main(String[] args) {
        Outer a = new Outer();
        Outer.Inner b = new Outer.Inner(a);
        b.fun();
    }
}

class Outer {
    private static int x = 4;
    int y;

    static class Inner {
        Outer outer;
        String name;
        static int member;

        Inner(Outer outer) {
            this.outer = outer;
        }

        void fun() {
            System.out.println(outer.y);
            System.out.println(x);
        }
    }
}

class BankAccount {
    private static class InterestCalculator {
        static double calculateYearly(double principal, double rate) {
            return principal * rate;
        }
    }

    public double computeInterest(double principal) {
        return InterestCalculator.calculateYearly(principal, 0.09);
    }
}

// use cases of static nested class:
/*
1. As helper class for any outer class
2. Builder design pattern
3. if you want to have static methods inside a nested class
4. Req/Res DTO 
*/