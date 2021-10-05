import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        int a = 42;
        System.out.println(((Integer)a).hashCode());

        int b = -42;
        System.out.println(((Integer)b).hashCode());

        Student s1 = new Student(3, 2, "Amy", "Potter");
        Student s2 = new Student(3, 2, "AMY", "Potter");
        System.out.println(s1.hashCode()); // 979331554
        System.out.println(s2.hashCode()); // 979331554

        HashSet<Student> hashSet = new HashSet<>();
        hashSet.add(s1);

        HashMap<Student, Integer> hashMap = new HashMap<>();
        hashMap.put(s2, 200);
    }
}
