package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.Iterator;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap MAP;

    public HistoryHandler(NGramMap map) {
        MAP = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        Iterator<String> it = q.words().iterator();
        while (it.hasNext()) {
            String word = it.next();
            labels.add(word);
            lts.add(MAP.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
