package payments;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EspiralMax {

    public static final String X = "X";
    public static final String Y = "Y";
    public static final List<EspiralCommand> espiralCommands = Collections.unmodifiableList(Arrays.asList(
            new EspiralCommand("RIGHT", X, 1),
            new EspiralCommand("UP", Y, 1),
            new EspiralCommand("LEFT", X, -1),
            new EspiralCommand("DOWN", Y, -1)
    ));

    /* Not thread safe */
    public static Long primesCount = 0L;
    public static Integer diagonalCount = 0;

    public static int getEspiralOrderWithDiagonalPrimesUnderPercentage(int minPercentage){
        //Initial espiral order and pointers
        int order = 7;
        int x = 0;
        int y = 0;
        Map<Pair<Integer, Integer>, Integer> espiral = new LinkedHashMap<>();
        Double currentEspiralPercentage = new Double(100);

        //Initialize first command
        Iterator<EspiralCommand> commandsIterator = espiralCommands.iterator();
        EspiralCommand currentCommand = commandsIterator.next();
        int commandOffset = 1; //Times that command must be used
        int commandExecutions = -1; //Times that command was used
        int commandsSwapped = 0; //Times that swap between commands

        int i = 1;
        boolean found = false;
        while (currentEspiralPercentage >= 10) {
            espiral.put(new Pair(x, y), i);
            commandExecutions++;

            //Closed current order espiral
            if (i == order*order) {
                currentEspiralPercentage = espiralPrimePercentage(espiral);

                //Reached the order desired
                if (currentEspiralPercentage < minPercentage) {
                    found = true;
                    break;
                } else {
                    order+=2;
                }
            }
            i++;

            //Max executions reached, must swap command
            if (commandExecutions == commandOffset) {
                if (!commandsIterator.hasNext()) {
                    commandsIterator = espiralCommands.iterator();
                }
                currentCommand = commandsIterator.next();
                commandsSwapped++;
                commandExecutions = 0;

                //Each two swaps must change commands offset
                if (commandsSwapped % 2 == 0) {
                    commandOffset++;
                }
            }

            //Move point for the next insert position
            if (currentCommand.getSource() == X) {
                x += 1 * currentCommand.getDirection();
            } else {
                y += 1 * currentCommand.getDirection();
            }
        }

        if (!found)
            throw new RuntimeException("OMG...");

        return order;
    }

    public static Double espiralPrimePercentage(Map<Pair<Integer, Integer>, Integer> espiral) {
        List<Integer> diagonalNumbers = espiral.entrySet().stream()
                .filter(e -> Math.abs(e.getKey().getKey()) == Math.abs(e.getKey().getValue())) // |x|==|y|
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        diagonalCount += diagonalNumbers.size();
        primesCount += diagonalNumbers.stream()
                .filter(i -> BigInteger.valueOf(i).isProbablePrime(100))
                .count();
        espiral.clear();

        return (primesCount * 1D / diagonalCount) * 100;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int order = getEspiralOrderWithDiagonalPrimesUnderPercentage(n);

        System.out.println(order);

        bufferedReader.close();
    }

    public static class EspiralCommand {
        private String name;
        private String source;
        private Integer direction;

        public EspiralCommand(String name, String source, Integer direction) {
            this.setName(name);
            this.setSource(source);
            this.setDirection(direction);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Integer getDirection() {
            return direction;
        }

        public void setDirection(Integer direction) {
            this.direction = direction;
        }
    }
}
