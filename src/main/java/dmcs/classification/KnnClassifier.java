package dmcs.classification;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KnnClassifier {
    private List<Pair<RealVector, Boolean>> trainingData;
    private DistanceMeasure distanceMeasure;

    public KnnClassifier(List<Pair<RealVector, Boolean>> trainingData, DistanceMeasure distanceMeasure) {
        this.trainingData = trainingData;
        this.distanceMeasure = distanceMeasure;
    }

    public boolean classify(RealVector data, int k) {
        var kNear = trainingData.stream()
                .map(row -> Pair.of(row, distanceMeasure.compute(data.toArray(), row.getLeft().toArray())))
                .sorted(Comparator.comparing(Pair::getRight))
                .limit(k)
                .map(Pair::getLeft)
                .collect(Collectors.toList());
        return vote(kNear);

    }

    private boolean vote(List<Pair<RealVector, Boolean>> kNear) {
        int trueVotes = 0;
        int falseVotes = 0;

        for (var row : kNear) {
            if (row.getRight()) {
                trueVotes++;
            } else {
                falseVotes++;
            }
        }
        int winningVotes = trueVotes > falseVotes ? trueVotes : falseVotes;
        System.out.println("TRUE: " + trueVotes + " FALSE: " + falseVotes + " RATIO: " + (double)winningVotes/ kNear.size());
        return trueVotes > falseVotes;
    }
}
