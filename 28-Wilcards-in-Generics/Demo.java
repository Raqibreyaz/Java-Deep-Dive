import java.util.ArrayList;
import java.util.List;

class Demo {
    public static void main(String[] args) {
        Animal[] animals = new Animal[2];
        animals[0] = new Animal();
        animals[1] = new Dog(); //works like a charm

        Dog[] dogs = new Dog[2];
        dogs[0] = new Dog();

        dogs[1] = (Dog) new Animal(); //won't work, fails at runtime
        animals = dogs; // works like a charm
        animals[1] = new Animal(); // fails at runtime


        List<Dog> dogs2 = new ArrayList<>();
        List <Animal> animals2 = dogs; //compilation error
    }
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