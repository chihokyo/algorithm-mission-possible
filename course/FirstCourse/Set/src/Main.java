import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        FileOperation.readFile("pride-prejudice.txt", words);
        System.out.println("Total words: " + words.size()); // 一共多少单词

        BSTSet<String> set = new BSTSet<>();
        for (String word : words) {
            set.add(word);
        }
        System.out.println("Total different words: " + set.getSize()); // 使用集合计算不同单词
    }
}
