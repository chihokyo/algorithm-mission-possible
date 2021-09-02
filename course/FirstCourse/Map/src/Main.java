import java.text.MessageFormat;
import java.util.ArrayList;

public class Main {
    private static double testMap(Map<String, Integer> map, String filename) {
        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.add(word, 1);
                }
            }
            System.out.println("Total different words: " + map.getSize());
            String targetWord1 = "pride";
            String targetWord2 = "prejudice";
            System.out.println(MessageFormat.format("Frequency of {0}: {1}", targetWord1, map.get(targetWord1)));
            System.out.println(MessageFormat.format("Frequency of {0}: {1}", targetWord2, map.get(targetWord2)));
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;

    }

    public static void main(String[] args) {
        String filename = "pride-prejudice.txt";
        BSTMap<String, Integer> bstMap = new BSTMap<>();
        double time1 = testMap(bstMap, filename);
        System.out.println("BST Set: " + time1 + " s");

        System.out.println();

        LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
        double time2 = testMap(linkedListMap, filename);
        System.out.println("LinkedList Set: " + time2 + " s");
    }
}
