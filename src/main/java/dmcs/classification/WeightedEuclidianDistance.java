package dmcs.classification;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class WeightedEuclidianDistance implements DistanceMeasure {

    private final ArrayRealVector weightVector;
    private final DistanceMeasure euclideanDistance = new EuclideanDistance();

    public WeightedEuclidianDistance(double[] weightVector) {
        this.weightVector = new ArrayRealVector(weightVector);
    }

    @Override
    public double compute(double[] a, double[] b) throws DimensionMismatchException {
        ArrayRealVector na = weightVector.ebeMultiply(new ArrayRealVector(a));
        ArrayRealVector nb = weightVector.ebeMultiply(new ArrayRealVector(b));
        return euclideanDistance.compute(na.toArray(), nb.toArray());
    }
}
