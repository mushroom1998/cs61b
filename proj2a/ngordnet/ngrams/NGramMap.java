package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    private String WORDSFILENAME;
    private String COUNTSFILENAME;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        WORDSFILENAME = wordsFilename;
        COUNTSFILENAME = countsFilename;
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        In in = new In(WORDSFILENAME);
        TimeSeries ts = new TimeSeries();
        while(in.hasNextLine()) {
            String wordInFile = in.readString();
            if (!wordInFile.equals(word) && ts.size()!=0) {
                break;
            }
            else if (wordInFile.equals(word)) {
                ts.put(in.readInt(), in.readDouble());
            }
            in.readLine();
        }
        return ts;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(countHistory(word), startYear, endYear);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        In in = new In(COUNTSFILENAME);
        TimeSeries ts = new TimeSeries();
        while(in.hasNextLine()) {
            String[] info = in.readLine().split(",");
            ts.put(Integer.parseInt(info[0]), Double.parseDouble(info[1]));
        }
        return ts;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        return countHistory(word).dividedBy(totalCountHistory());
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return new TimeSeries(weightHistory(word), startYear, endYear);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        Iterator<String> it = words.iterator();
        TimeSeries summedWordsWeight = countHistory(it.next());
        while (it.hasNext()) {
            summedWordsWeight = summedWordsWeight.plus(countHistory(it.next()));
        }
        return summedWordsWeight.dividedBy(totalCountHistory());
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        return new TimeSeries(summedWeightHistory(words), startYear, endYear);
    }


}
