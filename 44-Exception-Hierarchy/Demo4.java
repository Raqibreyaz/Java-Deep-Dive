public class Demo4 {
    public static void main(String[] args) {

        try {
            checkEligibility(-13);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkEligibility(int age) throws InvalidAgeException {
        if (age <= 0) {
            throw new InvalidAgeException("Invalid age" + " " + age);
        }

        if (age > 18) {
            System.out.println("You are eligible to vote");
        }
    }
}

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}