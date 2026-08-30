let A = 0;
let B = 0;

let registers = [0, 0, 0, 0, 0, 0, 0, 0];

let PC = 0;
let SP = 7;

let carryFlag = false;
let zeroFlag = false;

let program = [];
let currentInstruction = null;
let running = false;


function formatByte(value) {
    return (value & 0xFF)
        .toString(16)
        .toUpperCase()
        .padStart(2, "0");
}


function formatWord(value) {
    return (value & 0xFFFF)
        .toString(16)
        .toUpperCase()
        .padStart(4, "0");
}


function updateZeroFlag() {
    zeroFlag = A === 0;
}


function parseValue(value) {

    value = value.trim();

    if (value.startsWith("#")) {
        value = value.substring(1);
    }

    if (value.startsWith("0x") ||
        value.startsWith("0X")) {

        return parseInt(
            value.substring(2),
            16
        );
    }

    if (value.endsWith("H") ||
        value.endsWith("h")) {

        return parseInt(
            value.substring(0, value.length - 1),
            16
        );
    }

    return parseInt(value, 10);
}


function loadProgram() {

    const input =
        document.getElementById("programInput");

    program = input.value
        .split(/\r?\n/)
        .map(line => line.trim())
        .filter(line => line.length > 0);

    A = 0;
    B = 0;

    registers = [0, 0, 0, 0, 0, 0, 0, 0];

    PC = 0;
    SP = 7;

    carryFlag = false;
    zeroFlag = false;

    currentInstruction = null;
    running = false;

    document.getElementById("status").textContent =
        "Program loaded";

    document.getElementById("trace").textContent =
        "Program loaded. Press STEP to execute.";

    updateDisplay();
    displayProgram();
}


function resetSimulator() {

    A = 0;
    B = 0;

    registers = [0, 0, 0, 0, 0, 0, 0, 0];

    PC = 0;
    SP = 7;

    carryFlag = false;
    zeroFlag = false;

    currentInstruction = null;
    running = false;

    document.getElementById("status").textContent =
        "Ready";

    document.getElementById("trace").textContent =
        "Waiting for execution...";

    updateDisplay();
    displayProgram();
}


function displayProgram() {

    const display =
        document.getElementById("programDisplay");

    display.innerHTML = "";

    program.forEach((instruction, index) => {

        const line =
            document.createElement("div");

        line.className = "program-line";

        if (index === PC &&
            PC < program.length) {

            line.classList.add("current");
        }

        line.textContent =
            index.toString().padStart(2, "0") +
            "  " +
            instruction;

        display.appendChild(line);
    });
}


function fetchInstruction() {

    if (PC >= program.length) {
        return null;
    }

    currentInstruction =
        program[PC];

    PC++;

    return currentInstruction;
}


function decodeInstruction(instruction) {

    const parts =
        instruction.split(/\s+/);

    const name =
        parts[0].toUpperCase();

    const operand =
        parts.length > 1
            ? parts.slice(1).join(" ")
            : "";

    return {
        name: name,
        operand: operand
    };
}


function executeInstruction(instruction) {

    const name = instruction.name;
    const operand = instruction.operand;

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

            running = false;

            break;

        default:

            document.getElementById("status").textContent =
                "Unsupported instruction: " + name;
    }
}


function executeMOV(operand) {

    const parts =
        operand.split(",");

    if (parts.length !== 2) {
        return;
    }

    const destination =
        parts[0].trim().toUpperCase();

    const source =
        parts[1].trim().toUpperCase();

    if (!source.startsWith("#")) {
        return;
    }

    const value =
        parseValue(source) & 0xFF;

    if (destination === "A") {

        A = value;
        updateZeroFlag();

    } else if (destination === "B") {

        B = value;

    } else if (destination.startsWith("R")) {

        const index =
            parseInt(
                destination.substring(1),
                10
            );

        if (index >= 0 && index <= 7) {

            registers[index] = value;
        }
    }
}


function executeADD(operand) {

    const upperOperand =
        operand.toUpperCase();

    if (!upperOperand.startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(
            operand.substring(3)
        );

    const result =
        A + value;

    carryFlag =
        result > 255;

    A =
        result & 0xFF;

    updateZeroFlag();
}


function executeSUBB(operand) {

    const upperOperand =
        operand.toUpperCase();

    if (!upperOperand.startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(
            operand.substring(3)
        );

    const borrow =
        carryFlag ? 1 : 0;

    const result =
        A - value - borrow;

    carryFlag =
        result < 0;

    A =
        result & 0xFF;

    updateZeroFlag();
}


function executeANL(operand) {

    const upperOperand =
        operand.toUpperCase();

    if (!upperOperand.startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(
            operand.substring(3)
        );

    A =
        A & value;

    updateZeroFlag();
}


function executeINC(operand) {

    const target =
        operand.trim().toUpperCase();

    if (target === "A") {

        A =
            (A + 1) & 0xFF;

        updateZeroFlag();

    } else if (target.startsWith("R")) {

        const index =
            parseInt(
                target.substring(1),
                10
            );

        if (index >= 0 && index <= 7) {

            registers[index] =
                (registers[index] + 1) & 0xFF;
        }
    }
}


function executeSJMP(operand) {

    const offset =
        parseValue(operand);

    PC =
        PC + offset;

    if (PC < 0) {
        PC = 0;
    }

    if (PC > program.length) {
        PC = program.length;
    }
}


function executeCLR(operand) {

    if (operand.trim().toUpperCase() === "A") {

        A = 0;

        updateZeroFlag();
    }
}


function step() {

    if (program.length === 0) {

        loadProgram();
    }

    if (PC >= program.length) {

        document.getElementById("status").textContent =
            "Program finished";

        return;
    }

    const oldA = A;
    const oldPC = PC;

    const instructionText =
        program[PC];

    const instruction =
        fetchInstruction();

    if (instruction === null) {
        return;
    }

    let trace =
        "Instruction : " +
        instructionText +
        "\n\n";

    trace += "FETCH ✓\n";

    const decoded =
        decodeInstruction(instruction);

    trace += "DECODE ✓\n";

    executeInstruction(decoded);

    trace += "EXECUTE ✓\n\n";

    trace +=
        "A : " +
        formatByte(oldA) +
        " → " +
        formatByte(A) +
        "\n";

    trace +=
        "PC : " +
        formatWord(oldPC) +
        " → " +
        formatWord(PC);

    document.getElementById("trace").textContent =
        trace;

    if (decoded.name === "END") {

        running = false;

        document.getElementById("status").textContent =
            "Program terminated";

    } else {

        document.getElementById("status").textContent =
            "Running";
    }

    updateDisplay();
    displayProgram();
}


function runProgram() {

    if (program.length === 0) {
        loadProgram();
    }

    running = true;

    let safetyCounter = 0;

    while (running &&
           PC < program.length) {

        step();

        safetyCounter++;

        if (safetyCounter >= 1000) {

            running = false;

            document.getElementById("status").textContent =
                "Stopped: execution limit reached";
        }
    }
}


function updateDisplay() {

    document.getElementById("A").textContent =
        formatByte(A);

    document.getElementById("B").textContent =
        formatByte(B);

    for (let i = 0; i < 8; i++) {

        document.getElementById("R" + i).textContent =
            formatByte(registers[i]);
    }

    document.getElementById("PC").textContent =
        formatWord(PC);

    document.getElementById("SP").textContent =
        formatByte(SP);

    document.getElementById("CY").textContent =
        carryFlag ? "1" : "0";

    document.getElementById("Z").textContent =
        zeroFlag ? "1" : "0";
}


resetSimulator();
