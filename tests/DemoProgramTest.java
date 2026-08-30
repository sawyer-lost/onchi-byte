public class DemoProgramTest {

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
        simulator.run();

        int finalA = simulator.getCPU().getA();
        int finalR0 = simulator.getCPU().getRegister(0);

        System.out.println("DEMO PROGRAM TEST");
        System.out.println("=================");

        System.out.println("Final A  : "
                + String.format("%02X", finalA));

        System.out.println("Final R0 : "
                + String.format("%02X", finalR0));

        System.out.println("Status   : "
                + simulator.getExecutionStatus());

        System.out.println();

        if (finalA == 6
                && finalR0 == 3
                && simulator.getExecutionStatus()
                    .equals("Program terminated")) {

            System.out.println("DEMO TEST : PASS");

        } else {

            System.out.println("DEMO TEST : FAIL");
        }
    }
}
