package wordmap;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String args[]) {
        try {
            Occurrences occ = FileWalker.getOccurrences("src/test_dir");
            FileWriter fileWriter = new FileWriter("src/out.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(occ);
            printWriter.close();
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
