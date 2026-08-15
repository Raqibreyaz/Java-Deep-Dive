import java.util.function.*;

public class Demo5 {
    public static void main(String[] args) {
        Predicate<Student> passed = s -> s.marks > 40;
        Predicate<Student> isAdult = s -> s.age >= 18;
        Predicate<Student> isEligible = s -> passed.and(isAdult).test(s);

        System.out.println(isEligible.test(new Student(42, 17)));
    }
}

class Student {
    int marks;
    int age;

    public Student(int marks, int age) {
        this.marks = marks;
        this.age = age;
    }
}
