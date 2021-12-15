
package wordmap;

import java.util.Map;
import java.util.Set;

import java.util.TreeMap;
import java.util.TreeSet;

public class Occurrences
{
    private Map< String, Map< String, Set< Position >>> occ;
    // Maps words -> filename -> sets of their Positions in the file.

    public Occurrences( )
    {
        occ = new TreeMap<> ( );
    }

    public void put( String word, String filename, Position pos )
    {
        if (!occ.containsKey(word)) {
            occ.put(word, new TreeMap<>());
        }
        if (!occ.get(word).containsKey(filename)) {
            occ.get(word).put(filename, new TreeSet<>());
        }
        occ.get(word).get(filename).add(pos);
    }


    public int countWords( )
    {
        int result = 0;
        for (String w : occ.keySet()) {
            result += count(w);
        }
        return result;
    }

    public int count( )
    {
        return occ.size();
    }

    public int count( String word )
    {
        if (occ.containsKey(word)) {
            int result = 0;
            for (String f : occ.get(word).keySet()) {
                result += count(word, f);
            }
            return result;
        }
        return 0;
    }

    public int count( String word, String file )
    {
        if (FileWalker.seemsOK(file)) {
            return occ.get(word).get(file).size();
        }
        return 0;
    }

    public String toString( ) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        for (Map.Entry<String, Map<String, Set<Position>>> word : occ.entrySet())
            {
            stringBuilder.append("word \"").append(word.getKey()).append("\" occurs ").append(count(word.getKey())).append(" times:\n");

            for (Map.Entry<String, Set<Position>> file : word.getValue().entrySet())
            {
                stringBuilder.append("   in file \"").append(file.getKey().replace("\\", "/")).append("\":\n");

                for (Position pos : file.getValue())
                {
                    stringBuilder.append("      ").append(pos.toString()).append("\n");
                }
            }
        }

        stringBuilder.append("\noccurrences      ").append(countWords()).append("\ndistinct words   ").append(count());
        return stringBuilder.toString();
    }
}
