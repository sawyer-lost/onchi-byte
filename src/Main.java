import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String filePath = "../programs/demo.txt";

        List<String> programList = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    programList.add(line);
                }
            }

        } catch (IOException e) {

            System.out.println("Error reading demo.txt");
            System.out.println(e.getMessage());
            return;
        }

        String[] program =
                programList.toArray(new String[0]);

        Simulator simulator =
                new Simulator();

        simulator.loadProgram(program);

        System.out.println();
        System.out.println("================================");
        System.out.println("       ONCHI-BYTE SIMULATOR");
        System.out.println("================================");
        System.out.println();

        System.out.println("PROGRAM LOADED");
        System.out.println("--------------------------------");

        for (int i = 0; i < program.length; i++) {

            System.out.println(
                    String.format(
                            "%04X  %s",
                            i,
                            program[i]
                    )
            );
        }

        System.out.println();
        System.out.println("================================");
        System.out.println("        STEP-BY-STEP EXECUTION");
        System.out.println("================================");
        System.out.println();

        boolean finished = false;
        int stepNumber = 1;

        while (!finished && stepNumber <= 1000) {

            simulator.step();

            if (simulator.getCurrentInstruction() == null) {
                break;
            }

            System.out.println(
                    "STEP " + stepNumber
            );

            System.out.println(
                    "Instruction : "
                    + simulator.getCurrentInstruction()
            );

            System.out.println();

            System.out.println(
                    simulator.getExecutionTrace()
            );

            System.out.println("CPU STATE");
            System.out.println("--------------------------------");

            System.out.println(
                    "A  : "
                    + String.format(
                            "%02X",
                            simulator.getCPU().getA()
                    )
            );

            System.out.println(
                    "B  : "
                    + String.format(
                            "%02X",
                            simulator.getCPU().getB()
                    )
            );

            for (int i = 0; i < 8; i++) {

                System.out.println(
                        "R" + i + " : "
                        + String.format(
                                "%02X",
                                simulator.getCPU()
                                        .getRegister(i)
                        )
                );
            }

            System.out.println(
                    "PC : "
                    + String.format(
                            "%04X",
                            simulator.getCPU().getPC()
                    )
            );

            System.out.println(
                    "SP : "
                    + String.format(
                            "%02X",
                            simulator.getCPU().getSP()
                    )
            );

            System.out.println(
                    "CY : "
                    + (
                            simulator.getCPU().isCarryFlag()
                                    ? "1"
                                    : "0"
                    )
            );

            System.out.println(
                    "Z  : "
                    + (
                            simulator.getCPU().getA() == 0
                                    ? "1"
                                    : "0"
                    )
            );

            System.out.println(
                    "STATUS : "
                    + simulator.getExecutionStatus()
            );

            System.out.println();
            System.out.println(
                    "================================"
            );
            System.out.println();

            if (
                    simulator.getExecutionStatus()
                            .equals("Program terminated")
            ) {

                finished = true;
            }

            stepNumber++;
        }

        System.out.println();
        System.out.println("================================");
        System.out.println("          FINAL CPU STATE");
        System.out.println("================================");
        System.out.println();

        System.out.println(
                "A  : "
                + String.format(
                        "%02X",
                        simulator.getCPU().getA()
                )
        );

        System.out.println(
                "B  : "
                + String.format(
                        "%02X",
                        simulator.getCPU().getB()
                )
        );

        for (int i = 0; i < 8; i++) {

            System.out.println(
                    "R" + i + " : "
                    + String.format(
                            "%02X",
                            simulator.getCPU()
                                    .getRegister(i)
                    )
            );
        }

        System.out.println(
                "PC : "
                + String.format(
                        "%04X",
                        simulator.getCPU().getPC()
                )
        );

        System.out.println(
                "SP : "
                + String.format(
                        "%02X",
                        simulator.getCPU().getSP()
                )
        );

        System.out.println(
                "CY : "
                + (
                        simulator.getCPU().isCarryFlag()
                                ? "1"
                                : "0"
                )
        );

        System.out.println(
                "Z  : "
                + (
                        simulator.getCPU().getA() == 0
                                ? "1"
                                : "0"
                )
        );

        System.out.println();

        System.out.println(
                "STATUS : "
                + simulator.getExecutionStatus()
        );

        System.out.println();
        System.out.println("================================");
        System.out.println("       PROGRAM EXECUTION END");
        System.out.println("================================");
    }
}
