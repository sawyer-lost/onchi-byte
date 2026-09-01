public class Simulator {

    private CPU cpu;
    private Memory memory;
    private InstructionSet instructionSet;

    private Instruction currentInstruction;

    private String executionStatus;
    private StringBuilder executionTrace;


    public Simulator() {

        cpu = new CPU();
        memory = new Memory();
        instructionSet = new InstructionSet();

        executionStatus = "Ready";
        executionTrace = new StringBuilder();
    }


    // FETCH
    public Instruction fetch() {

        int pc = cpu.getPC();

        String instructionText =
                memory.readInstruction(pc);

        if (instructionText == null) {

            executionStatus = "Program finished";

            return null;
        }

        currentInstruction =
                instructionSet.createInstruction(instructionText);

        cpu.incrementPC();

        executionTrace.append("FETCH ✓\n");

        return currentInstruction;
    }


    // DECODE
    public Instruction decode() {

        if (currentInstruction == null) {
            return null;
        }

        executionTrace.append("DECODE ✓\n");

        return currentInstruction;
    }


    // EXECUTE
    public void execute() {

        if (currentInstruction == null) {
            return;
        }

        String name =
                currentInstruction.getName();

        String operand =
                currentInstruction.getOperand();

        executionTrace.append("EXECUTE ✓\n");


        switch (name) {

            case "MOV":
                executeMOV(operand);
                break;

            case "ADD":
                executeADD(operand);
                break;

            case "SUBB":
                executeSUBB(operand);
                break;

            case "ANL":
                executeANL(operand);
                break;

            case "INC":
                executeINC(operand);
                break;

            case "SJMP":
                executeSJMP(operand);
                break;

            case "CLR":
                executeCLR(operand);
                break;

            case "END":
                executionStatus =
                        "Program terminated";
                break;

            default:
                executionStatus =
                        "Unsupported instruction: " + name;
        }
    }


    // MOV
    private void executeMOV(String operand) {

        String[] parts =
                operand.split(",");

        if (parts.length != 2) {

            executionStatus =
                    "Invalid MOV instruction";

            return;
        }

        String destination =
                parts[0].trim().toUpperCase();

        String source =
                parts[1].trim().toUpperCase();


        if (source.startsWith("#")) {

            int value =
                    parseValue(source);

            if (destination.equals("A")) {

                cpu.setA(value);

            } else if (destination.equals("B")) {

                cpu.setB(value);

            } else if (destination.startsWith("R")) {

                int registerNumber =
                        Integer.parseInt(
                                destination.substring(1)
                        );

                if (registerNumber >= 0 &&
                        registerNumber <= 7) {

                    cpu.setRegister(
                            registerNumber,
                            value
                    );
                }
            }
        }
    }


    // ADD
    private void executeADD(String operand) {

        if (!operand.toUpperCase().startsWith("A,#")) {

            executionStatus =
                    "Invalid ADD instruction";

            return;
        }

        String valueText =
                operand.substring(3).trim();

        int value =
                parseValue(valueText);

        int result =
                cpu.getA() + value;

        cpu.setCarryFlag(result > 255);

        cpu.setA(result & 0xFF);
    }


    // SUBB
    private void executeSUBB(String operand) {

        if (!operand.toUpperCase().startsWith("A,#")) {

            executionStatus =
                    "Invalid SUBB instruction";

            return;
        }

        String valueText =
                operand.substring(3).trim();

        int value =
                parseValue(valueText);

        int borrow =
                cpu.isCarryFlag() ? 1 : 0;

        int result =
                cpu.getA() - value - borrow;

        cpu.setCarryFlag(result < 0);

        cpu.setA(result & 0xFF);
    }


    // ANL
    private void executeANL(String operand) {

        if (!operand.toUpperCase().startsWith("A,#")) {

            executionStatus =
                    "Invalid ANL instruction";

            return;
        }

        String valueText =
                operand.substring(3).trim();

        int value =
                parseValue(valueText);

        cpu.setA(
                cpu.getA() & value
        );
    }


    // INC
    private void executeINC(String operand) {

        if (operand.equalsIgnoreCase("A")) {

            cpu.setA(
                    (cpu.getA() + 1) & 0xFF
            );

        } else if (
                operand.toUpperCase().startsWith("R")
        ) {

            int registerNumber =
                    Integer.parseInt(
                            operand.substring(1).trim()
                    );

            int value =
                    (cpu.getRegister(registerNumber) + 1)
                    & 0xFF;

            cpu.setRegister(
                    registerNumber,
                    value
            );

        } else {

            executionStatus =
                    "Invalid INC instruction";
        }
    }


    // SJMP
    private void executeSJMP(String operand) {

        int offset =
                parseValue(operand);

        int newPC =
                cpu.getPC() + offset;

        cpu.setPC(newPC);
    }


    // CLR
    private void executeCLR(String operand) {

        if (operand.equalsIgnoreCase("A")) {

            cpu.setA(0);

        } else {

            executionStatus =
                    "Invalid CLR instruction";
        }
    }


    // STEP
    public void step() {

        executionTrace.setLength(0);

        Instruction instruction =
                fetch();

        if (instruction == null) {
            return;
        }

        decode();

        execute();

        if (!executionStatus.equals(
                "Program terminated")) {

            executionStatus = "Running";
        }
    }


    // RUN
    public void run() {

        executionStatus = "Running";

        int safetyCounter = 0;

        while (
                !executionStatus.equals(
                        "Program terminated"
                )
                &&
                safetyCounter < 1000
        ) {

            step();

            safetyCounter++;
        }

        if (safetyCounter >= 1000) {

            executionStatus =
                    "Stopped: execution limit reached";
        }
    }


    // Load program
    public void loadProgram(String[] program) {

        memory.reset();

        cpu.reset();

        for (int i = 0; i < program.length; i++) {

            if (
                    program[i] != null
                    &&
                    !program[i].trim().isEmpty()
            ) {

                memory.writeInstruction(
                        i,
                        program[i].trim()
                );
            }
        }

        executionStatus =
                "Program loaded";
    }


    // Reset
    public void reset() {

        cpu.reset();

        memory.reset();

        currentInstruction = null;

        executionTrace.setLength(0);

        executionStatus =
                "Ready";
    }


    // Parse hexadecimal / decimal values
    private int parseValue(String value) {

        value = value.trim();

        // Remove immediate-value symbol
        if (value.startsWith("#")) {
            value = value.substring(1).trim();
        }

        // Hexadecimal with 0x prefix
        if (
                value.startsWith("0x")
                ||
                value.startsWith("0X")
        ) {

            return Integer.parseInt(
                    value.substring(2),
                    16
            );
        }

        // Hexadecimal with H suffix
        if (
                value.endsWith("H")
                ||
                value.endsWith("h")
        ) {

            return Integer.parseInt(
                    value.substring(
                            0,
                            value.length() - 1
                    ),
                    16
            );
        }

        // Hexadecimal values containing A-F
        if (
                value.matches("[0-9A-Fa-f]+")
                &&
                value.matches(".*[A-Fa-f].*")
        ) {

            return Integer.parseInt(
                    value,
                    16
            );
        }

        // Normal decimal value
        return Integer.parseInt(value);
    }


    public CPU getCPU() {
        return cpu;
    }


    public Memory getMemory() {
        return memory;
    }


    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }


    public String getExecutionStatus() {
        return executionStatus;
    }


    public String getExecutionTrace() {
        return executionTrace.toString();
    }
}
