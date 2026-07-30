public class Demo8 {
    public static void main(String[] args) {
        C c = new C();
        c.fun();
    }
}

// JAVA resolution priority rule
interface A {
    default void fun(){
        System.out.println("Inside A interface");
    }
}

// class's implementation will get priority
class B {
    public void fun(){
        System.out.println("Inside B class");
    }
}

class C extends B implements A{
    @Override
    public void fun(){
        System.out.println("Inside C class");
    }
}