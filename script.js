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


function resetCPU() {

    A = 0;
    B = 0;

    registers = [0, 0, 0, 0, 0, 0, 0, 0];

    PC = 0;
    SP = 7;

    carryFlag = false;
    zeroFlag = false;

    currentInstruction = null;

    updateDisplay();
}


function loadProgram() {

    const text =
        document.getElementById("programInput").value;

    program = text
        .split("\n")
        .map(line => line.trim())
        .filter(line => line.length > 0);

    resetCPU();

    document.getElementById("status").textContent =
        "Program loaded";

    displayProgram();
}


function displayProgram() {

    const display =
        document.getElementById("programDisplay");

    display.innerHTML = "";

    for (let i = 0; i < program.length; i++) {

        const line =
            document.createElement("div");

        line.className = "program-line";

        if (i === PC) {
            line.classList.add("current");
        }

        line.textContent =
            i.toString().padStart(2, "0") +
            "  " +
            program[i];

        display.appendChild(line);
    }
}


function fetchInstruction() {

    if (PC >= program.length) {

        currentInstruction = null;

        return null;
    }

    currentInstruction = program[PC];

    PC++;

    return currentInstruction;
}


function decodeInstruction(instruction) {

    const parts =
        instruction.split(/\s+/);

    const name =
        parts[0].toUpperCase();

    let operand = "";

    if (parts.length > 1) {
        operand = parts.slice(1).join(" ");
    }

    return {
        name: name,
        operand: operand
    };
}


function executeInstruction(decoded) {

    const name = decoded.name;
    const operand = decoded.operand;

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

            document.getElementById("status").textContent =
                "Program terminated";

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
        parseValue(source.substring(1));

    if (destination === "A") {

        A = value;
        updateZeroFlag();

    } else if (destination.startsWith("R")) {

        const index =
            parseInt(destination.substring(1));

        if (index >= 0 && index <= 7) {
            registers[index] = value;
        }
    }
}


function executeADD(operand) {

    if (!operand.toUpperCase().startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(operand.substring(2));

    const result =
        A + value;

    carryFlag = result > 255;

    A = result & 0xFF;

    updateZeroFlag();
}


function executeSUBB(operand) {

    if (!operand.toUpperCase().startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(operand.substring(2));

    const borrow =
        carryFlag ? 1 : 0;

    const result =
        A - value - borrow;

    carryFlag = result < 0;

    A = result & 0xFF;

    updateZeroFlag();
}


function executeANL(operand) {

    if (!operand.toUpperCase().startsWith("A,#")) {
        return;
    }

    const value =
        parseValue(operand.substring(2));

    A = A & value;

    updateZeroFlag();
}


function executeINC(operand) {

    if (operand.toUpperCase() === "A") {

        A = (A + 1) & 0xFF;

        updateZeroFlag();

        return;
    }

    if (operand.toUpperCase().startsWith("R")) {

        const index =
            parseInt(
                operand.substring(1)
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

    PC = PC + offset;

    if (PC < 0) {
        PC = 0;
    }

    if (PC > program.length) {
        PC = program.length;
    }
}


function executeCLR(operand) {

    if (operand.toUpperCase() === "A") {

        A = 0;

        updateZeroFlag();
    }
}


function parseValue(value) {

    value = value.trim();

    if (value.startsWith("0x")
        || value.startsWith("0X")) {

        return parseInt(
            value.substring(2),
            16
        );
    }

    if (value.endsWith("H")
        || value.endsWith("h")) {

        return parseInt(
            value.substring(
                0,
                value.length - 1
            ),
            16
        );
    }

    return parseInt(value);
}


function updateZeroFlag() {

    zeroFlag = A === 0;
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

    let trace =
        "Instruction : " +
        program[PC] +
        "\n\n";

    const instruction =
        fetchInstruction();

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

    if (decoded.name !== "END") {

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

    while (running && PC < program.length) {

        step();

        if (program[PC - 1]
            && program[PC - 1]
                .toUpperCase() === "END") {

            running = false;
        }
    }
}


function resetSimulator() {

    resetCPU();

    document.getElementById("status").textContent =
        "Ready";

    document.getElementById("trace").textContent =
        "Waiting for execution...";

    displayProgram();
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


function formatByte(value) {

    return value
        .toString(16)
        .toUpperCase()
        .padStart(2, "0");
}


function formatWord(value) {

    return value
        .toString(16)
        .toUpperCase()
        .padStart(4, "0");
}


resetSimulator();
