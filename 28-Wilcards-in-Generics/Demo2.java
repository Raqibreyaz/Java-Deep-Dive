import java.util.ArrayList;
import java.util.List;

class Demo2 {
    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        dogs.add(new Dog());

        fun(dogs);
    }

    static void fun(List<?> objects) {
        for (Object object : objects)
            System.out.println(object.getClass().getName());

        // objects.add(new Object()); // wont work
        if (objects.get(0).getClass().getName().equals("Dog")) {
            // objects.add(new Dog()); // wont work
        }
    }

    // static void fun(List<Animal> animals) {
    // for (Animal animal : animals)
    // animal.eat();
    // }
}

class Animal {
    void eat() {
        System.out.println("Eating food");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("dog is barking");
    }
}