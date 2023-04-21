package ngordnet.wordnet;

import com.sun.source.tree.Tree;
import edu.princeton.cs.algs4.In;
import org.eclipse.jetty.util.ArrayUtil;

import java.util.*;

public class WordNetDataSet {
    private String HYPONYMS;
    private String SYNSETS;
    private int NumberOfWords = 0;
    private WordNetGraph wn;
    private HashMap<Integer, TreeSet<String>> WordDataSet = new HashMap<>();

    public WordNetDataSet(String HyponymsFile, String SynsetsFile) {
        HYPONYMS = HyponymsFile;
        SYNSETS = SynsetsFile;
        NumberOfWords = CreateWordSet();
        wn = new WordNetGraph(HYPONYMS, NumberOfWords);
    }

    public int CreateWordSet() {
        In in = new In(SYNSETS);
        while(in.hasNextLine()) {
            NumberOfWords += 1;

            String[] info = in.readLine().split(",");
            int v = Integer.parseInt(info[0]);

            String[] words = info[1].split(" ");
            TreeSet<String> wordSet= new TreeSet<>();
            for(String word:words) {
                wordSet.add(word);
            }
            WordDataSet.put(v, wordSet);
        }
        return NumberOfWords;
    }

    public Set hyponyms(String s) {
        String[] inputWords = s.replace(" ", "").split(",");
        Set<String>[] hyposSet = new Set[inputWords.length];

        for(int i=0; i<inputWords.length; i++) {
            hyposSet[i] = new TreeSet<String>();
            Set<Integer> allIndex = getAllIndex(inputWords[i]);
            for (int index : allIndex) {
                hyposSet[i].addAll(words(wn.FindHypos(index)));
            }
        }
        Set<Integer> ret = intersect(hyposSet);
        return intersect(hyposSet);
    }

    public Set intersect(Set<String>[] hyposSet) {
        Set<String> intersectHypo= hyposSet[0];
        for(int i=1; i<hyposSet.length; i++) {
            intersectHypo.retainAll(hyposSet[i]);
        }
        return intersectHypo;
    }

    public HashSet getAllIndex(String s) {
        HashSet<Integer> index = new HashSet<>();

        for(int key:WordDataSet.keySet()) {
            if (contains(WordDataSet.get(key), s)) {
                index.add(key);
            }
        }

        return index;
    }

    public boolean contains(TreeSet<String>dataset, String s) {
        for (String word:dataset) {
            if(word.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public int GetWordsNumber() {
        return NumberOfWords;
    }

    public TreeSet words(int index) {
        return WordDataSet.get(index);
    }

    public TreeSet words(Set<Integer> indexes) {
        TreeSet<String> hyposWords = new TreeSet<>();
        for(int key:indexes) {
            Iterator it = words(key).iterator();
            while(it.hasNext()) {
                hyposWords.add((String)it.next());
            }
        }
        return hyposWords;
    }


}
