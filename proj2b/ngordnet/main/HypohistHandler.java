package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import ngordnet.wordnet.WordNetDataSet;
import org.knowm.xchart.XYChart;

import java.util.*;

public class HypohistHandler extends NgordnetQueryHandler {
    private NGramMap MAP;
    private WordNetDataSet WNDS;

    public HypohistHandler(NGramMap map, WordNetDataSet wnds) {
        MAP = map;
        WNDS = wnds;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        String words = String.join(",",q.words());
        int k = q.k();
        int startYear = (k==0 || q.startYear()==0)?1900:q.startYear();
        int endYear = (k==0 || q.endYear()==0)?2020:q.endYear();

        TreeMap<Double, String> wordAppearMap = countAppearance(WNDS.hyponyms(words), startYear, endYear);
        List<String> freqWords = getMostFrequentWords(wordAppearMap, k);

        Iterator<String> it = freqWords.iterator();
        while (it.hasNext()) {
            String word = it.next();
            labels.add(word);
            lts.add(MAP.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }

    public TreeMap countAppearance(Set<String> words, int startYear, int endYear) {
        TreeMap<Double, String> wordAppearMap = new TreeMap<>(Collections.reverseOrder());

        for(String word:words) {
            TimeSeries ts = MAP.countHistory(word, startYear, endYear);
            Double appearance =ts.data().stream().mapToDouble(Double::doubleValue).sum();
            if(appearance!=0) {
                wordAppearMap.put(appearance, word);
            }
        }

        return wordAppearMap;
    }

    public List<String> getMostFrequentWords(TreeMap<Double, String> freqMap, int k) {
        if (k >= freqMap.size() || k == 0) {
            List<String> ret = new ArrayList<>(new TreeSet<>(freqMap.values()));
            Collections.sort(ret);
            return ret;
        } else {
            List<String> ret = new ArrayList<>();
            Iterator<Double> it = freqMap.keySet().iterator();
            for (int i = 0; i < k; i++) {
                ret.add(freqMap.get(it.next()));
            }
            return ret;
        }
    }
}
