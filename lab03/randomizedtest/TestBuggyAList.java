package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> CorrectL = new AListNoResizing<>();
        BuggyAList<Integer> BuggyL = new BuggyAList<>();

        CorrectL.addLast(4);
        BuggyL.addLast(4);
        CorrectL.addLast(5);
        BuggyL.addLast(5);
        CorrectL.addLast(6);
        BuggyL.addLast(6);

        CorrectL.removeLast();
        BuggyL.removeLast();
        assertEquals(CorrectL.get(1), BuggyL.get(1));
        assertEquals(CorrectL.get(2), BuggyL.get(2));

        CorrectL.removeLast();
        BuggyL.removeLast();
        assertEquals(CorrectL.get(1), BuggyL.get(1));

        CorrectL.removeLast();
        BuggyL.removeLast();
        assertEquals(CorrectL.get(0), BuggyL.get(0));
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BuggyList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BuggyList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                assertEquals(L.size(), BuggyList.size());
            } else if (operationNumber == 2 && L.size() > 0 && BuggyList.size() > 0) {
                //getLast
                assertEquals(L.size(), BuggyList.size());
            } else if (operationNumber == 3 && L.size() > 0 && BuggyList.size() > 0) {
                //removeLast
                assertEquals(L.removeLast(), BuggyList.removeLast());
            } else if (operationNumber == 4 && L.size() > 0 && BuggyList.size() > 0) {
                int randVal = StdRandom.uniform(0, L.size());
                assertEquals(L.get(randVal), BuggyList.get(randVal));
            }
        }
    }
}
