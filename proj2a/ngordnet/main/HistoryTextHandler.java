package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.Iterator;
import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap MAP;

    public HistoryTextHandler(NGramMap map) {
        MAP = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            String word = it.next();
            response += word + ": " +
                    MAP.weightHistory(word, startYear, endYear).toString() + "\n";
        }

        return response;
    }
}
