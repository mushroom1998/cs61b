package ngordnet.ngrams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i=startYear; i<=endYear; i++) {
            if (ts.containsKey(i)) {
                put(i, ts.get(i));
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        LinkedList<Integer> yearList = new LinkedList<>();
        for (int i=firstKey(); i<=lastKey(); i++) {
            if(containsKey(i)) {
                yearList.add(i);
            }
        }
        return yearList;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        LinkedList<Double> dataList = new LinkedList<>();
        List<Integer> yearList = years();
        Iterator<Integer> it = yearList.iterator();
        while(it.hasNext()) {
            dataList.add(get(it.next()));
        }
        return dataList;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries retTS = new TimeSeries();
        List<Integer> yearList = Stream.of(years(), ts.years()).flatMap(Collection::stream)
                .distinct().collect(Collectors.toList()); //merge two yearList
        Iterator<Integer> it = yearList.iterator();
        while(it.hasNext()) {
            int key = it.next();
            if (containsKey(key) && ts.containsKey(key)) {
                retTS.put(key, get(key) + ts.get(key));
            }
            else if (containsKey(key)) {
                retTS.put(key, get(key));
            }
            else {
                retTS.put(key, ts.get(key));
            }
        }
        return retTS;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries retTS = new TimeSeries();
         List<Integer> yearList = Stream.of(years(), ts.years()).flatMap(Collection::stream)
                 .distinct().collect(Collectors.toList()); //merge two yearList
         Iterator<Integer> it = yearList.iterator();
         while(it.hasNext()) {
             int key = it.next();
             if (containsKey(key) && ts.containsKey(key)) {
                 retTS.put(key, get(key) / ts.get(key));
             }
             else if (!ts.containsKey(key)) {
                 throw new IllegalArgumentException("ts doesn't contains year " + key);
             }
             else {
                 retTS.put(key, 0.0);
             }
         }
         return retTS;
    }
}
