package ngordnet.proj2b_testing;

import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.wordnet.WordNetDataSet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNetDataSet wnds = new WordNetDataSet(hyponymFile, synsetFile);
        return new HyponymsHandler(ngm, wnds);
    }
}
