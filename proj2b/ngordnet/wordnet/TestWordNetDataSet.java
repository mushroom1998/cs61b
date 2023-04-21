package ngordnet.wordnet;

import org.junit.Test;

import java.util.*;
import static org.junit.Assert.*;


public class TestWordNetDataSet {
    @Test
    public void testCreateWordSet() {
        WordNetDataSet wnds = new WordNetDataSet(
                "./data/wordnet/hyponyms16.txt", "./data/wordnet/synsets16.txt");

        String[] expectedDataSet1 = {"happening", "occurrence", "occurrent", "natural_event"};
        int expectedWordNummber = 16;

        assertEquals(expectedDataSet1, wnds.words(1));
        assertEquals(expectedWordNummber, wnds.GetWordsNumber());
    }

    @Test
    public void testSingleInputHyponyms() {
        WordNetDataSet wnds = new WordNetDataSet(
                "./data/wordnet/hyponyms16.txt", "./data/wordnet/synsets16.txt");

        Set<String> expectedHyponymsChange = new TreeSet<String>();
        String[] expectedHyponymsChangeSet = {"alteration", "change", "demotion",
                "increase", "jump", "leap", "modification", "saltation", "transition", "variation"};
        for(String s:expectedHyponymsChangeSet) {
            expectedHyponymsChange.add(s);
        }

        assertEquals(expectedHyponymsChange, wnds.hyponyms("change"));
    }

    @Test
    public void testMultipleInputHyponymsSmallInputfile() {
        WordNetDataSet wnds = new WordNetDataSet(
                "./data/wordnet/hyponyms16.txt", "./data/wordnet/synsets16.txt");
        Set<String> expectedHyponyms = new TreeSet<String>();
        String[] expectedHyponymsSet = {"alteration", "change", "increase", "jump", "leap", "modification",
                "saltation", "transition"};
        for(String s:expectedHyponymsSet) {
            expectedHyponyms.add(s);
        }

        assertEquals(expectedHyponyms, wnds.hyponyms("occurrence, change"));
        assertEquals(expectedHyponyms, wnds.hyponyms("change, occurrence"));
    }

    @Test
    public void testMultipleInputHyponymsLargeInputfile() {
        WordNetDataSet wnds = new WordNetDataSet(
                "./data/wordnet/hyponyms.txt", "./data/wordnet/synsets.txt");

        Set<String> expectedHyponyms1 = new TreeSet<String>();
        Set<String> expectedHyponyms2 = new TreeSet<String>();
        Set<String> expectedHyponyms = new TreeSet<String>();

        String[] expectedHyponymsSet1 = {"amazon", "bird", "cat", "chick", "dam", "demoiselle", "female",
                "female_mammal", "filly", "hag", "hen", "nanny", "nymph", "siren"};
        String[] expectedHyponymsSet2 = {"crown_princess", "marchioness", "materfamilias", "matriarch", "mayoress",
                "mistress", "vicereine", "viscountess"};
        String[] expectedHyponymsSet = {"amphitheater", "amphitheatre"};
        for(String s:expectedHyponymsSet1) {
            expectedHyponyms1.add(s);
        }
        for(String s:expectedHyponymsSet2) {
            expectedHyponyms2.add(s);
        }
        for(String s:expectedHyponymsSet) {
            expectedHyponyms.add(s);
        }

        assertEquals(expectedHyponyms, wnds.hyponyms("bowl, gallery"));
        assertEquals(expectedHyponyms1, wnds.hyponyms("female, animal"));
        assertEquals(expectedHyponyms2, wnds.hyponyms("female, leader"));
    }
}
