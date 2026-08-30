public class Main {

    public static void main(String[] args) {

        Simulator simulator = new Simulator();

        String[] program = {
            "MOV A,#05",
            "MOV R0,#03",
            "ADD A,#03",
            "INC A",
            "ANL A,#0F",
            "SUBB A,#02",
            "END"
        };

        simulator.loadProgram(program);

        System.out.println("ONCHI-BYTE SIMULATOR");
        System.out.println("====================");

        System.out.println("\nProgram loaded.");

        while (!simulator.getExecutionStatus()
                .equals("Program terminated")) {

            simulator.step();

            System.out.println("\nCurrent Instruction: "
                    + simulator.getCurrentInstruction());

            System.out.println(simulator.getExecutionTrace());

            System.out.println(simulator.getCPU().getState());

            System.out.println("Status: "
                    + simulator.getExecutionStatus());

            System.out.println("--------------------");
        }

        System.out.println("\nProgram execution completed.");
    }
}
