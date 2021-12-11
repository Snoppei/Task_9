package ru.sc.vsu.berezin_y_a;

import java.io.IOException;
import java.util.*;

public class Cmd {

    public void runCmd() throws IOException {

        Logic logic = new Logic();
        CmdCommands cmdCommand = CmdCommands.STATIC;
        Scanner scn = new Scanner(System.in);

        String command;
        String inputFile;
        String outputFile;

        System.out.println("Commands:");
        System.out.println("-r   - run program from console");
        System.out.println("-h   - show help page");
        System.out.println("-e   - exit from program");
        System.out.println("-i   - enter input file and run program");
        System.out.println("-o   - enter output file and run program");

        while (cmdCommand != CmdCommands.EXIT) {
            List list = new LinkedList();
            command = scn.next();
            if (Objects.equals(command, "-r")) {
                cmdCommand = CmdCommands.RUN;
            } else if (Objects.equals(command, "-h")) {
                cmdCommand = CmdCommands.HELP;
            } else if (Objects.equals(command, "-e")) {
                cmdCommand = CmdCommands.EXIT;
            } else if (Objects.equals(command, "-i")) {
                cmdCommand = CmdCommands.ENTER_INPUT_FILE;
            } else if (Objects.equals(command, "-o")) {
                cmdCommand = CmdCommands.ENTER_OUTPUT_FILE;
            } else {
                System.out.println("Error, this command is not found. Try again: ");
            }

            switch (cmdCommand) {
                case EXIT -> System.exit(1);
                case RUN -> {
                    logic.fillList(list);
                    System.out.print("Answer: ");
                    logic.printCollectionInConsole(logic.createNewList(list));
                }
                case ENTER_INPUT_FILE -> {
                    System.out.print("Enter way to input file: ");
                    inputFile = scn.next();
                    int[] arr = Util.readIntArrayFromFile(inputFile);
                    list = logic.intArrayToList(arr);
                    System.out.print("Answer: ");
                    logic.printCollectionInConsole(logic.createNewList(list));

                }
                case ENTER_OUTPUT_FILE -> {
                    logic.fillList(list);
                    System.out.print("Enter way to output file: ");
                    outputFile = scn.next();
                    logic.createNewList(list);
                    Util.writeListToFile(outputFile, list);
                }
                case HELP -> {
                    System.out.println("Commands:");
                    System.out.println("-run     - run program from console");
                    System.out.println("-help    - show help page");
                    System.out.println("-exit    - exit from program");
                    System.out.println("-input   - enter input file and run program");
                    System.out.println("-output  - enter output file");
                }
            }

        }

    }

}
