public class Demo6 {
    public static void main(String[] args) {
        Vehicle car = new Car();
        car.drive();

        Vehicle.brake();
    }
}

// After Java 8 --> Default Methods, Static Methods
// From Java 9 --> Private Methods

interface Vehicle {
    default void drive() {
        System.out.println("Vehicle is being drived");
        accelarate();
    }

    static void brake() {
        System.out.println("Vehicle is applying brake");
    }

    private void accelarate(){
        System.out.println("Vehicle got accelarated");
    }
}

class Car implements Vehicle {
    // @Override
    // public void drive() {
    //     System.out.println("Car is being drived");
    // }
}