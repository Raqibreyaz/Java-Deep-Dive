public class Demo {
    public static void main(String[] args) {
        Car c = new BlackThar();
        c.drive();
    }
}

interface Car {
    void drive();
};

abstract class Thar implements Car {
    abstract public void drive();
}

class BlackThar extends Thar {
    @Override
    public void drive(){}
}