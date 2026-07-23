public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("raquib", 7212686, 32, "IIT DELHI");

        s1.printDetails();
        System.out.println(s1);
    }
}

class Student {
    String name;
    int rollNo;
    int age;
    String collegeName;

    Student() {
        this(null, 0, 0, null);
    }

    Student(String name, int rollNo, int age, String collegeName) {
        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
        this.collegeName = collegeName;
    }

    void markAttendance() {        
        System.out.printf("Attendance marked by %s\n", name);
    }

    void printDetails() {
        System.out.println(this);
        System.out.printf("Name: %s\n", name);
        System.out.printf("Roll No: %d\n", rollNo);
        System.out.printf("Age: %d\n", age);
        System.out.printf("College: %s\n", collegeName);
    }
}