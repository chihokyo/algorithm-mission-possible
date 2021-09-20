import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileOperation {

    /**
     * 读取文件，并将所有单词放入到words
     *
     * @param fileName 文件名
     * @param words    单词数
     * @return 是否成功
     */
    public static boolean readFile(String fileName, ArrayList<String> words) {

        if (fileName == null || words == null) {

            System.out.println("filename is null or word is null");
            return false;
        }

        Scanner scanner;

        try {
            File file = new File(fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), StandardCharsets.UTF_8);
                scanner.useLocale(Locale.ENGLISH);
            } else {
                return false;
            }
        } catch (IOException ios) {
            System.out.println("Cannot open " + fileName);
            return false;
        }

        // 简单分词
        if (scanner.hasNextLine()) {
            String contents = scanner.useDelimiter("\\A").next();
            int start = firstCharacterIndex(contents, 0);
            for (int i = start + 1; i <= contents.length(); ) {
                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {
                    String word = contents.substring(start, i).toLowerCase();
                    words.add(word);
                    start = firstCharacterIndex(contents, i);
                    i = start + 1;
                } else {
                    i++;
                }
            }
        }

        return true;
    }

    /**
     * 寻找字符串中，从start位置开始的第一个字母字符的位置
     *
     * @param s     字符串
     * @param start 开始位置
     * @return int 开始的位置
     */
    private static int firstCharacterIndex(String s, int start) {
        for (int i = start; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) return i;
        }
        return s.length();
    }

}
