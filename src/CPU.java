public class CPU {

    // Main registers
    private int A;
    private int B;

    // General purpose registers
    private int[] registers;

    // Special registers
    private int PC;
    private int SP;

    // Program Status Word
    private boolean carryFlag;
    private boolean zeroFlag;

    public CPU() {
        registers = new int[8];
        reset();
    }

    public void reset() {
        A = 0;
        B = 0;
        PC = 0;
        SP = 7;

        carryFlag = false;
        zeroFlag = false;

        for (int i = 0; i < registers.length; i++) {
            registers[i] = 0;
        }
    }

    // Accumulator
    public int getA() {
        return A;
    }

    public void setA(int value) {
        A = value & 0xFF;
        updateZeroFlag();
    }

    // B register
    public int getB() {
        return B;
    }

    public void setB(int value) {
        B = value & 0xFF;
    }

    // General purpose registers R0-R7
    public int getRegister(int index) {
        if (index < 0 || index > 7) {
            throw new IllegalArgumentException("Register must be between R0 and R7");
        }

        return registers[index];
    }

    public void setRegister(int index, int value) {
        if (index < 0 || index > 7) {
            throw new IllegalArgumentException("Register must be between R0 and R7");
        }

        registers[index] = value & 0xFF;
    }

    // Program Counter
    public int getPC() {
        return PC;
    }

    public void setPC(int value) {
        PC = value & 0xFFFF;
    }

    public void incrementPC() {
        PC++;
        PC = PC & 0xFFFF;
    }

    // Stack Pointer
    public int getSP() {
        return SP;
    }

    public void setSP(int value) {
        SP = value & 0xFF;
    }

    // Carry flag
    public boolean isCarryFlag() {
        return carryFlag;
    }

    public void setCarryFlag(boolean value) {
        carryFlag = value;
    }

    // Zero flag
    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public void updateZeroFlag() {
        zeroFlag = (A == 0);
    }

    // Display CPU state
    public String getState() {
        StringBuilder state = new StringBuilder();

        state.append("CPU STATE\n");
        state.append("A  : ").append(String.format("%02X", A)).append("\n");
        state.append("B  : ").append(String.format("%02X", B)).append("\n");

        for (int i = 0; i < registers.length; i++) {
            state.append("R").append(i)
                 .append(" : ")
                 .append(String.format("%02X", registers[i]))
                 .append("\n");
        }

        state.append("PC : ").append(String.format("%04X", PC)).append("\n");
        state.append("SP : ").append(String.format("%02X", SP)).append("\n");
        state.append("CY : ").append(carryFlag ? "1" : "0").append("\n");
        state.append("Z  : ").append(zeroFlag ? "1" : "0").append("\n");

        return state.toString();
    }
}
