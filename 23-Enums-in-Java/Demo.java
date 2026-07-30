public class Demo {
    public static void main(String[] args) {
        int status = PaymentStatus.SUCCESS;

        switch (status) {
            case PaymentStatus.SUCCESS:
                System.out.println("Payment successful!");
                break;

            case PaymentStatus.FAILED:
                System.out.println("Payment failed!");
                break;

            default:
                System.out.println("Invalid Payment status");
                break;
        }
    }
}

class PaymentStatus {
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;
    public static final int PENDING = 3;
}