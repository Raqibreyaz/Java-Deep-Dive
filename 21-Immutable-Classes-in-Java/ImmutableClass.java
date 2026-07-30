public class ImmutableClass {
    public static void main(String[] args) {
        Student s = new Student("Raquib", 32, new College("IIT Delhi", "Delhi"));

        Student.printDetails(s);
        s.getCollege().name = "IIIT Delhi";
        Student.printDetails(s);
    }
}

class Student {
    private final String name;
    private final int age;
    private final College college;

    Student(String name, int age, College college) {
        this.name = name;
        this.age = age;
        this.college = new College(college);
    }

    // getters
    public int getAge() {
        return this.age;
    }
    public String getName() {
        return this.name;
    }
    public College getCollege() {
        return new College(college);
    }

    static void printDetails(Student s) {
        System.out.printf("Name: %s\n", s.name);
        System.out.printf("Age: %d\n", s.age);
        System.out.printf("College: %s %s\n", s.college.name, s.college.address);
    }
}

class College {
    String name;
    String address;

    College(String name, String address) {
        this.name = name;
        this.address = address;
    }

    College(College college) {
        this.name = college.name;
        this.address = college.address;
    }
}