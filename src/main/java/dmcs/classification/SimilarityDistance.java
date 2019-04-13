package dmcs.classification;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class SimilarityDistance implements DistanceMeasure {
    @Override
    public double compute(double[] a, double[] b) throws DimensionMismatchException {
        if (a.length != b.length) {
            throw new DimensionMismatchException(a.length, b.length);
        }
        int same = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b[i]) {
                same++;
            }
        }
        return ((double) a.length - (double)same) / (double)a.length;
    }
}
