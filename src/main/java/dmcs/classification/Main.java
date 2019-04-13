package dmcs.classification;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;

public class Main {

    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Required 1 argument for k parameter");
        }

        check();
        doClassification(args[0]);
    }

    private static void check() throws IOException {
        Database database = new Database("C:\\Users\\Piotr Borczyk\\IdeaProjects\\classification\\src\\main\\resources\\klasyfikacja.txt");
        var data = new ArrayList<>(database.data);
        var trainingSet = data.subList(0, round(data.size() * 0.90F));
        var validationSet = data.subList(round(data.size() * 0.90F), data.size());
        KnnClassifier knnClassifier = new KnnClassifier(data, new EuclideanDistance());
        Database testDb = new Database("C:\\Users\\Piotr Borczyk\\IdeaProjects\\classification\\src\\main\\resources\\test.txt");
        int finalK = 5;
        int correct = 0;
        for (var row : validationSet) {
            if (row.getRight() ==   knnClassifier.classify(row.getLeft(), finalK)) {
                correct++;
            }
        }
        System.out.println("" + correct + " OUT OF " + validationSet.size());
    }

    private static void doClassification(String arg) throws IOException {
        Database database = new Database("C:\\Users\\Piotr Borczyk\\IdeaProjects\\classification\\src\\main\\resources\\klasyfikacja.txt");
        var data = new ArrayList<>(database.data);
        KnnClassifier knnClassifier = new KnnClassifier(data, new EuclideanDistance());
        Database testDb = new Database("C:\\Users\\Piotr Borczyk\\IdeaProjects\\classification\\src\\main\\resources\\test.txt");
        testDb.data.forEach(realVectorBooleanPair -> System.out.println(realVectorBooleanPair.getLeft().toString()
                + knnClassifier.classify(realVectorBooleanPair.getLeft(), Integer.valueOf(arg))));
    }

}
