public class Static {
    public static void main(String[] args) {
        Student s1 = new Student("raquib", 7212686, 32);

        s1.printDetails();

        print();
    }

    static void print() {
        System.out.println("hello");
    }
}

class Student {
    String name;
    int rollNo;
    int age;
    static String collegeName = "";
    static int batch;

    Student() {
        this(null, 0, 0);
    }

    Student(String name, int rollNo, int age) {
        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
    }

    void markAttendance() {
        System.out.printf("Attendance marked by %s\n", name);
    }

    void printDetails() {
        System.out.printf("Name: %s\n", name);
        System.out.printf("Roll No: %d\n", rollNo);
        System.out.printf("Age: %d\n", age);
        System.out.printf("College: %s\n", collegeName);
        System.out.printf("Batch: %d\n", batch);
    }

    static {
        collegeName = "IIT Delhi";
        batch = 2026;
    }
}