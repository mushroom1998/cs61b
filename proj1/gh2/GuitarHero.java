package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class GuitarHero {
    private static final String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString[] GSs = new GuitarString[37];
        for(int i=0; i<37; i++) {
            double concert = 440.0 * Math.pow(2, (i-24.0) / 12.0);
            GSs[i] = new GuitarString(concert);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) >= 0) {
                    GSs[keyboard.indexOf(key)].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;

            for (GuitarString gs:GSs
            ) {
                sample += gs.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString gs:GSs
                     ) {
                    gs.tic();
                }
        }
    }

}
