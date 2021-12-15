
package wordmap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;


public abstract class FileWalker
{

    public static boolean seemsOK( File f )
    {
        return f. exists( ) && f. canRead( );
    }

    public static boolean seemsOK( String filename )
    {
        return seemsOK( new File( filename ));
    }

    public static Occurrences getOccurrences( String filename )
            throws FileNotFoundException, IOException
    {
        Occurrences occ = new Occurrences( );
        addOccurrences( new File( filename ), occ );
        return occ;
    }


    public static void addOccurrences( File file, Occurrences occ )
            throws FileNotFoundException, IOException {
        File f = new File(String.valueOf(file));
        if (f.isFile()) {
            addOccurrences(new BufferedReader(new FileReader(file)), file.getPath(), occ);
        }
        else if (f.isDirectory()) {
            if (f.listFiles() == null) {
                return;
            }
            for (File files : Objects.requireNonNull(f.listFiles())) {
                addOccurrences(files, occ);
            }
        }
    }


    public static
    void addOccurrences( BufferedReader source, String sourcename,
                         Occurrences occ )
            throws IOException {
        int ch;
        String word = "";
        int line = 1;
        int column = 1;
        do {
            ch = source.read();

            if (!Syntax.isInWord((char)ch)) {
                if (word.length() > 0) {
                    occ.put(word.toLowerCase(), sourcename, new Position(line, column - word.length()));
                    word = "";
                }
            } else {
                word = word + (char)ch;
            }

            if (Syntax.isNewLine((char)ch)) {
                line += 1;
                column = 1;
            } else {
                column+=1;
            }

        } while (ch != -1);

        source.close();
    }
}


