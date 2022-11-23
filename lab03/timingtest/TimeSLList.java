package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int n;

        n = 1000;
        SLList<Integer> L1 = new SLList<>();
        for (int i=0; i<n; i++){
            L1.addLast(i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L1.getLast();
        }
        times.addLast(sw1.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 2000;
        SLList<Integer> L2 = new SLList<>();
        for (int i=0; i<n; i++){
            L2.addLast(i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L2.getLast();
        }
        times.addLast(sw2.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 4000;
        SLList<Integer> L3 = new SLList<>();
        for (int i=0; i<n; i++){
            L3.addLast(i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L3.getLast();
        }
        times.addLast(sw3.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 8000;
        SLList<Integer> L4 = new SLList<>();
        for (int i=0; i<n; i++){
            L4.addLast(i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L4.getLast();
        }
        times.addLast(sw4.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);


        n = 16000;
        SLList<Integer> L5 = new SLList<>();
        for (int i=0; i<n; i++){
            L5.addLast(i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L5.getLast();
        }
        times.addLast(sw5.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 32000;
        SLList<Integer> L6 = new SLList<>();
        for (int i=0; i<n; i++){
            L6.addLast(i);
        }
        Stopwatch sw6 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L6.getLast();
        }
        times.addLast(sw6.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 64000;
        SLList<Integer> L7 = new SLList<>();
        for (int i=0; i<n; i++){
            L7.addLast(i);
        }
        Stopwatch sw7 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L7.getLast();
        }
        times.addLast(sw7.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        n = 128000;
        SLList<Integer> L8 = new SLList<>();
        for (int i=0; i<n; i++){
            L8.addLast(i);
        }
        Stopwatch sw8 = new Stopwatch();
        for (int j=0; j<10000; j++){
            L8.getLast();
        }
        times.addLast(sw8.elapsedTime());
        Ns.addLast(n);
        opCounts.addLast(10000);

        printTimingTable(Ns, times, opCounts);
    }

}
