public class LocalClass {
    public static void main(String[] args) {
        Object o = Outer.greet();
    }
}

// gives compilation error as y can't be accessed via static method
/*
 * class Outer {
 * static void greet() {
 * System.out.println("Hello");
 * int y = 10;
 * 
 * class Local {
 * static void sayHello() {
 * System.out.println(y);
 * System.out.println("Hello");
 * }
 * }
 * Local.sayHello();
 * }
 * }
 */

// Effective final variable
class Outer {
    static Object greet() {
        int y = 5;
        // y++; no possible

        class Local {
            void sayHello() {
                System.out.println(y);
            }
        }

        Local local = new Local();
        local.sayHello();

        return local;
    }
}