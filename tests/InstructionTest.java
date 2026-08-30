public class InstructionTest {

    public static void main(String[] args) {

        int passed = 0;
        int failed = 0;

        // TC01 - MOV A,#05
        if (testMovA()) {
            passed++;
            System.out.println("TC01 MOV A,#05 : PASS");
        } else {
            failed++;
            System.out.println("TC01 MOV A,#05 : FAIL");
        }

        // TC02 - MOV R0,#03
        if (testMovR0()) {
            passed++;
            System.out.println("TC02 MOV R0,#03 : PASS");
        } else {
            failed++;
            System.out.println("TC02 MOV R0,#03 : FAIL");
        }

        // TC03 - ADD A,#03
        if (testAdd()) {
            passed++;
            System.out.println("TC03 ADD A,#03 : PASS");
        } else {
            failed++;
            System.out.println("TC03 ADD A,#03 : FAIL");
        }

        // TC04 - SUBB A,#02
        if (testSubb()) {
            passed++;
            System.out.println("TC04 SUBB A,#02 : PASS");
        } else {
            failed++;
            System.out.println("TC04 SUBB A,#02 : FAIL");
        }

        // TC05 - ANL A,#0F
        if (testAnl()) {
            passed++;
            System.out.println("TC05 ANL A,#0F : PASS");
        } else {
            failed++;
            System.out.println("TC05 ANL A,#0F : FAIL");
        }

        // TC06 - INC A
        if (testInc()) {
            passed++;
            System.out.println("TC06 INC A : PASS");
        } else {
            failed++;
            System.out.println("TC06 INC A : FAIL");
        }

        // TC07 - CLR A
        if (testClr()) {
            passed++;
            System.out.println("TC07 CLR A : PASS");
        } else {
            failed++;
            System.out.println("TC07 CLR A : FAIL");
        }

        System.out.println();
        System.out.println("======================");
        System.out.println("Tests Passed : " + passed);
        System.out.println("Tests Failed : " + failed);
        System.out.println("======================");
    }

    private static boolean testMovA() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#05"
        });

        simulator.step();

        return simulator.getCPU().getA() == 5;
    }

    private static boolean testMovR0() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV R0,#03"
        });

        simulator.step();

        return simulator.getCPU().getRegister(0) == 3;
    }

    private static boolean testAdd() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#05",
                "ADD A,#03"
        });

        simulator.step();
        simulator.step();

        return simulator.getCPU().getA() == 8;
    }

    private static boolean testSubb() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#08",
                "SUBB A,#02"
        });

        simulator.step();
        simulator.step();

        return simulator.getCPU().getA() == 6;
    }

    private static boolean testAnl() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#0F",
                "ANL A,#03"
        });

        simulator.step();
        simulator.step();

        return simulator.getCPU().getA() == 3;
    }

    private static boolean testInc() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#05",
                "INC A"
        });

        simulator.step();
        simulator.step();

        return simulator.getCPU().getA() == 6;
    }

    private static boolean testClr() {

        Simulator simulator = new Simulator();

        simulator.loadProgram(new String[]{
                "MOV A,#05",
                "CLR A"
        });

        simulator.step();
        simulator.step();

        return simulator.getCPU().getA() == 0;
    }
}
