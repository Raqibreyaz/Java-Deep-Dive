public class StaticMethodOverriding {
    public static void main(String[] args) {
        A a = new B();
        a.print();

        System.out.println(a.x);

        // a.fun2();
    }
}

// static -> they belong to class not objects -> no override
// private methods can't be overridden
// final methods can't be overridden
// properties/variables can't be overridden(no polymorphism)
// final class can't inherit a child class

final class A {
    int x = 10;

    static void print() {
        System.out.println("printed from class A");
    }

    private void fun2() {
        System.out.println("A's fun2()");
    }

    final void fun3() {
    }
}

class B extends A {
    int x = 15;

    // @Override
    static void print() {
        System.out.println("printed from class B");
    }

    void fun2() {
        System.out.println("B's fun2()");
    }

    // @Override
    // void fun3(){}
}