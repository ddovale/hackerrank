package payments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EspiralMax {

    public static final String COLUMN = "COL";
    public static final String ROW = "ROW";
    public static final List<EspiralCommand> espiralCommands = Collections.unmodifiableList(Arrays.asList(
            new EspiralCommand("RIGHT", COLUMN, 1),
            new EspiralCommand("UP", ROW, -1),
            new EspiralCommand("LEFT", COLUMN, -1),
            new EspiralCommand("DOWN", ROW, 1)
    ));

    public static int[][] generateEspiral(int n){

        int order = n;
        int rowPos = (order / 2);
        int colPos = (order % 2) == 0 ? (order / 2) - 1 : (order / 2);
        int[][] espiral = new int[order][order];

        //Initialize first command
        Iterator<EspiralCommand> commandsIterator = espiralCommands.iterator();
        EspiralCommand currentCommand = commandsIterator.next();
        int commandOffset = 1; //Times that command must be used
        int commandExecutions = -1; //Times that command was used
        int commandsSwapped = 0; //Times that swap between commands

        for (int i = 1; i <= order*order; i++) {
            espiral[rowPos][colPos] = i;
            commandExecutions++;

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

            //Move cursor for the next insert position
            if (currentCommand.getSource() == COLUMN) {
                colPos += 1 * currentCommand.getDirection();
            } else {
                rowPos += 1 * currentCommand.getDirection();
            }
        }

        return espiral;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());




        int[][] espiral = generateEspiral(n);
        for (int i = 0; i < espiral.length; i++) {
            for (int j = 0; j < espiral.length; j++) {
                System.out.print(" " + String.format("%02d", espiral[i][j]) + " ");
            }
            System.out.println();
        }





        System.out.println(n);

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
