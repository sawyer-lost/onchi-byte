import java.util.HashMap;
import java.util.Map;

public class InstructionSet {

    private Map<String, String> instructionCategories;

    public InstructionSet() {
        instructionCategories = new HashMap<>();

        // Data Transfer
        instructionCategories.put("MOV", "Data Transfer");

        // Arithmetic
        instructionCategories.put("ADD", "Arithmetic");
        instructionCategories.put("SUBB", "Arithmetic");

        // Logical
        instructionCategories.put("ANL", "Logical");

        // Increment / Decrement
        instructionCategories.put("INC", "Increment / Decrement");

        // Control Flow
        instructionCategories.put("SJMP", "Control Flow");

        // Program Termination
        instructionCategories.put("END", "Program Termination");

        // Extra logical instruction
        instructionCategories.put("CLR", "Logical");
    }

    public boolean isSupported(String instruction) {
        return instructionCategories.containsKey(instruction.toUpperCase());
    }

    public String getCategory(String instruction) {
        return instructionCategories.get(instruction.toUpperCase());
    }

    public Instruction createInstruction(String line) {

        line = line.trim();

        if (line.isEmpty()) {
            return null;
        }

        String[] parts = line.split("\\s+", 2);

        String name = parts[0].toUpperCase();
        String operand = "";

        if (parts.length > 1) {
            operand = parts[1].trim();
        }

        if (!isSupported(name)) {
            throw new IllegalArgumentException(
                "Unsupported instruction: " + name
            );
        }

        return new Instruction(
            name,
            operand,
            getCategory(name)
        );
    }

    public void displayInstructionSet() {

        System.out.println("Supported Instructions");
        System.out.println("----------------------");

        for (Map.Entry<String, String> entry : instructionCategories.entrySet()) {
            System.out.println(
                entry.getKey() + " - " + entry.getValue()
            );
        }
    }
}
