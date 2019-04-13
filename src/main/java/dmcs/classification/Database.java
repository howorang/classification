package dmcs.classification;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Database {

    private static final Map<String, Double> genderMapper = Map.of("MEZCZYZNA", -1D, "KOBIETA", 1D);
    private static final Map<String, Double> incomeMapper = Map.of("NISKIE", -1D, "SREDNIE", 0D, "WYSOKIE", 1D);
    private static final Map<String, Double> educationMapper = Map.of("PODSTAWOWE", -1D, "SREDNIE", 0D, "WYZSZE", 1D);
    private static final Map<String, Double> employmentMapper = Map.of(
            "BEZROBOTNY", -1D,
            "SAMOZATRUDNIENIE", -0.5D,
            "UMOWA_O_DZIELO", 0D,
            "UMOWA_NA_CZAS_OKRESLONY", 0.5D,
            "UMOWA_NA_CZAS_NIEOKRESLONY", 1D);
    private static final Map<String, Double> ageMapper = Map.of(
            "od_20_do_30", -1D,
            "od_30_do_40", -0.5D,
            "od_40_do_50", 0D,
            "od_50_do_60", 0.5D,
            "powyzej_60", 1D);

    public final List<Pair<RealVector, Boolean>> data;



    public Database(String filePath) throws IOException {
        data = Files.lines(Path.of(filePath))
                .map(line -> StringUtils.split(line, ' '))
                .peek(row -> {
                    if (row.length < 8) {
                        System.out.println(Arrays.toString(row));
                    }
                })
                .map(row -> Pair.of(vectorise(row), row.length == 8 ? Boolean.valueOf(row[7]) : null))
                .collect(Collectors.toUnmodifiableList());
    }

    private RealVector vectorise(String[] row) {
        String gender = row[2];
        String income = row[3];
        String education = row[4];
        String employment = row[5];
        String age = row[6];
        return new ArrayRealVector(new double[]{
                genderMapper.get(gender),
                incomeMapper.get(income),
                educationMapper.get(education),
                employmentMapper.get(employment),
                ageMapper.get(age)
        });
    }
}
