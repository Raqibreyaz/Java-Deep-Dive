import java.util.*;

public class Demo {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        list.add(new Student("raquib", 100));
        list.add(new Student("shivam", 90));
        list.add(new Student("krishna", 83));

        Collections.sort(list);
        for (Student student : list) {
            System.out.println(student.name + " " + student.marks);
        }

        List<Integer> list2 = new ArrayList<>();
        list2.add(10);
        list2.add(5);
        list2.add(20);

        Collections.sort(list2);
        System.out.println(list2);
    }
}

class Student implements Comparable<Student> {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public int compareTo(Student other) {
        if (this.marks != other.marks)
            return other.marks - this.marks;

        return this.name.compareTo(other.name);
    }
}