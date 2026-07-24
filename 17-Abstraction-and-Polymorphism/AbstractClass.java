public class AbstractClass {
    public static void main(String[] args) {
        Car car = new FuelCar();
        car.start();
        car.accelerate();
        car.brake();
    }
}

abstract class Car {
    void start() {
        System.out.println("Car started!");
    }

    abstract void accelerate();

    abstract void brake();
}

class ElectricCar extends Car {
    @Override
    void accelerate() {
        System.out.println("Electric car is accelerating");
    }

    @Override
    void brake() {
        System.out.println("Brake applied on Electric car");
    }
}

class FuelCar extends Car {
    @Override
    void accelerate() {
        System.out.println("Fuel car is accelerating");
    }

    @Override
    void brake() {
        System.out.println("Brake applied on Fuel car");
    }
}