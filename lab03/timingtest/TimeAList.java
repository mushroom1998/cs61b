package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        int n;

        n = 1000;
        AList<Integer> L1 = new AList<>();
        Stopwatch sw1 = new Stopwatch();
        for (int i=0; i<n; i++){
            L1.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw1.elapsedTime());

        n = 2000;
        AList<Integer> L2 = new AList<>();
        Stopwatch sw2 = new Stopwatch();
        for (int i=0; i<n; i++){
            L2.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw2.elapsedTime());

        n = 4000;
        AList<Integer> L3 = new AList<>();
        Stopwatch sw3 = new Stopwatch();
        for (int i=0; i<n; i++){
            L3.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw3.elapsedTime());

        n = 8000;
        AList<Integer> L4 = new AList<>();
        Stopwatch sw4 = new Stopwatch();
        for (int i=0; i<n; i++){
            L4.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw4.elapsedTime());

        n = 16000;
        AList<Integer> L5 = new AList<>();
        Stopwatch sw5 = new Stopwatch();
        for (int i=0; i<n; i++){
            L5.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw5.elapsedTime());

        n = 32000;
        AList<Integer> L6 = new AList<>();
        Stopwatch sw6 = new Stopwatch();
        for (int i=0; i<n; i++){
            L6.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw6.elapsedTime());

        n = 64000;
        AList<Integer> L7 = new AList<>();
        Stopwatch sw7 = new Stopwatch();
        for (int i=0; i<n; i++){
            L7.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw7.elapsedTime());

        n = 128000;
        AList<Integer> L8 = new AList<>();
        Stopwatch sw8 = new Stopwatch();
        for (int i=0; i<n; i++){
            L8.addLast(i);
        }
        Ns.addLast(n);
        times.addLast(sw8.elapsedTime());

        printTimingTable(Ns, times, Ns);
    }
}
