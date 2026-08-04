public class Demo7 {
    public static void main(String[] args) {
        Box<Fish> b1 = new Box<>();
    }
}

// a type T which extends class Animal and implements Swimmable
class Box<T extends Animal & Swimmable> {
    T value;
}

class Animal {
    void display() {
        System.out.println("Displaying Animal");
    }
}

interface Swimmable {
    void swim();
}

class Dog extends Animal {

}

class Fish extends Animal implements Swimmable {
    @Override
    public void swim() {

    }
}