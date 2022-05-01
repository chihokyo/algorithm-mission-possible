public class TestStudent {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TestStudent(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "TestStudent{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public static void main(String[] args) {
        OriginalArray<TestStudent> stus = new OriginalArray<>();
        stus.addLast(new TestStudent("Amy", 100));
        stus.addLast(new TestStudent("Tom", 90));
        stus.addLast(new TestStudent("Jeke", 200));
        System.out.println(stus);
    }
}
