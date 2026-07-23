class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "raquib";
        s1.age = 32;
        s1.rollNo = 7212686;
        s1.collegeName = "IIT Delhi";

        s1.markAttendance();
        s1.printDetails();
    }
}

class Student {
    String name;
    int rollNo;
    int age;
    String collegeName;

    void markAttendance() {
        System.out.printf("Attendance marked by %s\n", name);
    }

    void printDetails() {
        System.out.printf("Name: %s\n", name);
        System.out.printf("Roll No: %d\n", rollNo);
        System.out.printf("Age: %d\n", age);
        System.out.printf("College: %s\n", collegeName);
    }
}