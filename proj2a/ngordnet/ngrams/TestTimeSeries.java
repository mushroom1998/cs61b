package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testRangedTimeSeries() {
        TimeSeries bigRange = new TimeSeries();
        bigRange.put(1991, 0.0);
        bigRange.put(1992, 100.0);
        bigRange.put(1994, 200.0);
        bigRange.put(1995, 500.0);

        TimeSeries smallRange = new TimeSeries(bigRange, 1994, 1995);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1994, 1995));
        assertEquals(expectedYears, smallRange.years());

        List<Double> expectedData = new ArrayList<>
                (Arrays.asList(200.0, 500.0));
        for (int i = 0; i < expectedData.size(); i += 1) {
            assertEquals(expectedData.get(i), smallRange.data().get(i), 1E-10);
        }
    }

    @Test
    public void testDivide() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1995, 500.0);

        try{
            TimeSeries dividePopulation = catPopulation.dividedBy(dogPopulation);
        } catch (IllegalArgumentException e) {
            assertEquals(1, 1);
        }

        dogPopulation.put(1994, 400.0);
        TimeSeries dividePopulation = catPopulation.dividedBy(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1994, 1995));
        assertEquals(expectedYears, dividePopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.5, 0.0));
        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), dividePopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }
} 