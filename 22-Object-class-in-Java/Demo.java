import java.util.Objects;

public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException {
        // Student s = new Student();
        // s.name = "Raquib";
        // s.age = 25;
        // System.out.println(s.toString());
        // System.out.println(s);

        Student s1 = new Student();
        Student s2 = new Student();

        s1.name = "Raquib";
        s2.name = "Raquib";
        s1.age = 32;
        s2.age = 32;

        // System.out.println(s1.hashCode() == s2.hashCode());

        // System.out.println(s1.getClass().getName());
        // System.out.println(s1 instanceof Student);

        Student s3 = (Student) s1.clone();
        System.out.println(s3.toString());

        System.out.println(new int[1] instanceof Object);
    }
}

class Student extends Object implements Cloneable {
    String name;
    int age;

    @Override
    public String toString() {
        return String.format("{name: %s, age: %d}", name, age);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (obj == this)
            return true;

        // if not checked --> ClassCastException
        if (obj.getClass() != this.getClass())
            return false;

        Student s = (Student) obj;
        return this.name.equals(s.name) && this.age == s.age;
    }

    @Override
    public int hashCode() {
        // int result = 17;
        // result = result * 31 + age;
        // result = result * 31 + (name == null ? 0 : name.hashCode());

        // return result;

        return Objects.hash(name, age);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // shallow copy
        return super.clone();
    }
}