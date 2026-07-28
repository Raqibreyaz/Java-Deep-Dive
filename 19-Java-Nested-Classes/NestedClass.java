public class NestedClass {
    public static void main(String[] args) {

        // Outer outer = new Outer();
        // Outer.Inner inner = new outer.Inner(); X wrong
        // Outer.Inner inner = outer.new Inner();
        Outer.Inner inner = new Outer().new Inner();

        inner.fun();
        
        Outer.Inner.fun2();
    }
}

class Outer {
    int x = 10;

    class Inner {
        // Outer outer; implicitly available

        // int x = 20;

        void fun() {
            System.out.println(x);
            // System.out.println(Outer.this.x);
        }

        // not allowed till Java 16
        static void fun2() {
            System.out.println("Hello");
        }
    }
}