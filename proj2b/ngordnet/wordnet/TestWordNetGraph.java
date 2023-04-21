package ngordnet.wordnet;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class TestWordNetGraph {
    @Test
    public void testAddEdge() {
        WordNetGraph wn = new WordNetGraph("./data/wordnet/hyponyms11.txt", 11);

        List<Integer> expectedVertex0 = new ArrayList<>(Arrays.asList(1));
        List<Integer> expectedVertex5 = new ArrayList<>(Arrays.asList(6,7));

        assertEquals(expectedVertex0, wn.GetVertex(0));
        assertEquals(expectedVertex5, wn.GetVertex(5));
    }

    @Test
    public void testFindHypos() {
        WordNetGraph wn = new WordNetGraph("./data/wordnet/hyponyms16.txt", 16);

        Set<Integer> expectedHyponyms1 = new HashSet<>(Arrays.asList(1,2,3,4,5,11,12,13));
        Set<Integer> expectedHyponyms2 = new HashSet<>(Arrays.asList(2,3,4,5));

        assertEquals(expectedHyponyms2, wn.FindHypos(2));
        assertEquals(expectedHyponyms1, wn.FindHypos(1));
    }
}

