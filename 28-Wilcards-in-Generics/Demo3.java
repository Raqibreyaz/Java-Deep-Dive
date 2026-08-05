import java.util.ArrayList;
import java.util.List;

public class Demo3 {
    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        dogs.add(new Dog());

        fun(dogs);
    }

    static void fun(List<? extends Animal> objects) {
        Animal animal = objects.get(0);
        animal.eat();
        animal.data = 10;

        // objects.add(new Animal()); //wont work
    }
}

class Animal {
    int data;
    void eat() {
        System.out.println("Eating food");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("dog is barking");
    }
}