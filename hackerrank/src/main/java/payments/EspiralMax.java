package payments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EspiralMax {

    public static final String X = "X";
    public static final String Y = "Y";
    public static final List<EspiralCommand> espiralCommands = Collections.unmodifiableList(Arrays.asList(
            new EspiralCommand("RIGHT", X, 1),
            new EspiralCommand("UP", Y, 1),
            new EspiralCommand("LEFT", X, -1),
            new EspiralCommand("DOWN", Y, -1)
    ));

    public static int getEspiralOrderWithDiagonalPrimesUnderPercentage(int minPercentage) {
        //Initial espiral order and pointers
        int order = 7;
        int x = 0;
        int y = 0;
        int diagonalCount = 0;
        int primesCount = 0;
        Double currentEspiralPercentage = new Double(100);

        //Initialize first command
        Iterator<EspiralCommand> commandsIterator = espiralCommands.iterator();
        EspiralCommand currentCommand = commandsIterator.next();
        int commandOffset = 1; //Times that command must be used
        int commandExecutions = -1; //Times that command was used
        int commandsSwapped = 0; //Times that swap between commands

        int i = 1;
        while (currentEspiralPercentage >= 10) {
            if (Math.abs(x) == Math.abs(y)) {
                if (BigInteger.valueOf(i).isProbablePrime(100)) {
                    primesCount++;
                }
                diagonalCount++;
            }

            commandExecutions++;

            //Closed current order espiral
            if (i == order*order) {
                currentEspiralPercentage = (primesCount * 1D / diagonalCount) * 100;

                //Reached the order desired
                if (currentEspiralPercentage < minPercentage) {
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

        return order;
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
