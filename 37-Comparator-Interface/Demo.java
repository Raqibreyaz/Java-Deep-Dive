import java.util.*;

public class Demo {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Raquib", 97, 123));
        list.add(new Student("Reyaz", 95, 124));
        list.add(new Student("Imran", 93, 120));
        list.add(new Student("Rockstar", 91, 121));
        list.add(null);

        Comparator<Student> c1 = new SortByName();
        Comparator<Student> c2 = new SortByMarks();
        Comparator<Student> c3 = new SortByRollNo();

        Collections.sort(list, (s1, s2) -> s1.marks - s2.marks);

        for (Student student : list) {
            System.out.println(student.name + ", " + student.marks + ", " + student.rollNo);
        }
    }
}

class Student {
    String name;
    int marks;
    int rollNo;

    Student(String name, int marks, int rollNo) {
        this.name = name;
        this.marks = marks;
        this.rollNo = rollNo;
    }
}

class SortByName implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
}

class SortByMarks implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
}

class SortByRollNo implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.rollNo - s2.rollNo;
    }
}