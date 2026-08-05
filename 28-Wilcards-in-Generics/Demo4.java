import java.util.ArrayList;
import java.util.List;

public class Demo4 {
    public static void main(String[] args) {
        List<Animal> list = new ArrayList<>();
        list.add(new Animal());
        list.add(new Animal());

        fun(list);
    }

    public static void fun(List<? super Animal> list){
        list.add(new Animal());
        list.add(new Dog());
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