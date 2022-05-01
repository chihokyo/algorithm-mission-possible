import java.util.Locale;
import java.util.Objects;

public class Student {
    int grade;
    int cls;
    String firstName;
    String lastName;

    public Student(int grade, int cls, String firstName, String lastName) {
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        // 判断是否同一个引用
        if (this == o) return true;
        // 判断是否为空，当前类对象是否和o对象一样
        if (o == null || getClass() != o.getClass()) return false;
        // 强制类型转换
        Student student = (Student) o;
        // 一个个成员变量进行比较
        return grade == student.grade && cls == student.cls && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        int B = 31;
        int hash = 0;
        hash = hash * B + grade;
        hash = hash * B + cls;
        hash = hash * B + firstName.toLowerCase().hashCode(); // 不区分大小写
        hash = hash * B + lastName.toLowerCase().hashCode();
        return hash;
    }
}
