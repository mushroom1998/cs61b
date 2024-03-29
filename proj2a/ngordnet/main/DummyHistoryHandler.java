package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.Iterator;


public class DummyHistoryHandler extends NgordnetQueryHandler {
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());

        TimeSeries parabola = new TimeSeries();
        for (int i = 0; i < 100; i += 1) {
            parabola.put(i, (i - 50.0) * (i - 50.0) + 3);
        }

        TimeSeries sinWave = new TimeSeries();
        for (int i = 0; i < 100; i += 1) {
            sinWave.put(i, 1000 + 500 * Math.sin(i/100.0*2*Math.PI));
        }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        labels.add("parabola");
        labels.add("sine wave");

        lts.add(parabola);
        lts.add(sinWave);

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
