public class Demo4 {
    public static void main(String[] args) {

    }
}

// Multiple inheritance -> interfaces
interface A {
    void fun();
}

interface B {
    void fun2();
}

class C implements A, B {
    @Override
    public void fun(){}

    @Override
    public void fun2(){}
}