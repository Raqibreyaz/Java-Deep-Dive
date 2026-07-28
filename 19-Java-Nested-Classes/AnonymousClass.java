public class AnonymousClass {
    public static void main(String[] args) {
        // Person p = new Guest();
        // p.introduce();

        Person p = new Person() {
            @Override
            void introduce() {
                greet();
                System.out.println("Hi i am a Guest");
            }

            void greet() {
                System.out.println("hello");
            }
        };

        p.introduce();
        // p.greet(); not possible
    }
}

class Person {
    void introduce() {
        System.out.println("Hi i am a person");
    }
}

// class Guest extends Person {
// @Override
// void introduce() {
// System.out.println("Hi i am a Guest!");
// }
// }

/*
Static Nested Class
Inner Class
Anonymous Class --> Lambdas
Local Class
*/