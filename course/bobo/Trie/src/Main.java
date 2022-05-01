import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-prejudice.txt", words)) {
            // BSTSet数据类型
            long startTime = System.nanoTime();
            BSTSet<String> bstSet = new BSTSet<>();
            for (String word : words) {
                bstSet.add(word);
            }
            for (String word : words) {
                bstSet.contains(word);
            }
            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1_000_000_000.0;

            System.out.println("Total different words " + bstSet.getSize());
            System.out.println("BSTSet " + time + "s");

            // Trie数据类型
            startTime = System.nanoTime();
            Trie trie = new Trie();
            for (String word : words) {
                trie.add(word);
            }
            for (String word : words) {
                trie.contains(word);
            }
            endTime = System.nanoTime();

            time = (endTime - startTime) / 1_000_000_000.0;

            System.out.println("Total different words " + trie.getSize());
            System.out.println("Trie " + time + "s");
        }
    }
}
