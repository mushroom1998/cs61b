package ngordnet.main;

import edu.princeton.cs.algs4.Graph;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.wordnet.WordNetDataSet;
import spark.utils.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private NGramMap MAP;
    private WordNetDataSet WNDS;

    public HyponymsHandler(NGramMap map, WordNetDataSet wnds) {
        MAP = map;
        WNDS = wnds;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String words = String.join(",",q.words());
        int k = q.k();
        if(k==0) {
            return new ArrayList<String>(new TreeSet<>(WNDS.hyponyms(words))).toString();
        }

        int startYear = (q.startYear()==0)?1900:q.startYear();
        int endYear = (q.endYear()==0)?2020:q.endYear();

        Set<String> allHypoWords= WNDS.hyponyms(words);
        TreeMap<Double, String> wordAppearMap = new TreeMap<>(Collections.reverseOrder());

        for(String word:allHypoWords) {
            TimeSeries ts = MAP.countHistory(word, startYear, endYear);
            Double appearance =ts.data().stream().mapToDouble(Double::doubleValue).sum();
            if(appearance!=0) {
                wordAppearMap.put(appearance, word);
            }
        }

        if(k>=wordAppearMap.size() || k==0) {
            List<String> ret = new ArrayList<>(new TreeSet<>(wordAppearMap.values()));
            Collections.sort(ret);
            return ret.toString();
        } else {
            List<String> ret = new ArrayList<>();
            Iterator<Double> it = wordAppearMap.keySet().iterator();
            for(int i=0; i<k; i++) {
                ret.add(wordAppearMap.get(it.next()));
            }
            Collections.sort(ret);
            return ret.toString();
        }
    }

}
