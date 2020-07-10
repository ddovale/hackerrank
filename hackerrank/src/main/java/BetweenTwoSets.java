import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'getTotalX' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER_ARRAY b
     */

    public static int getTotalX(List<Integer> a, List<Integer> b) {
        int maxValue = Math.max(a.stream().max(Comparator.naturalOrder()).get(), b.stream().max(Comparator.naturalOrder()).get());
        int countBetweenSets = 0;

        for(int i = 1; i <= maxValue; i++) {
            int finalI = i;

            boolean allFromAAreFactors = a.stream()
                    .mapToInt(value -> finalI % value)
                    .allMatch(mod -> mod == 0);

            boolean isFactorOfAllFromB = b.stream()
                    .mapToInt(value -> value % finalI)
                    .allMatch(mod -> mod == 0);

            if (allFromAAreFactors && isFactorOfAllFromB)
                countBetweenSets++;
        }

        return countBetweenSets;
    }

}

public class BetweenTwoSets {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> brr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int total = Result.getTotalX(arr, brr);

        System.out.println(total);

        bufferedReader.close();
    }
}
