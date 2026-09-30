# MicroOS-Sim — Week 4

## Week 4 Multi-Process Simulator

Week 4 separates the simulator into three processes:

```text
UI Process
    |
    | POSIX FIFO Named Pipe
    v
Core Process
    |
    +-- CPU
    +-- Memory
    +-- Stack
    +-- Queue
    |
    | POSIX FIFO Named Pipe
    v
Logging Process
    |
    v
simulator.log
```

### IPC mechanism

This implementation uses **POSIX FIFO (Named Pipes)**.

FIFOs:
- `/tmp/microos_ui_to_core.fifo`
- `/tmp/microos_core_to_ui.fifo`
- `/tmp/microos_core_to_log.fifo`

FIFO is suitable here because it is a simple OS-level IPC mechanism for one-way message streams between the independent UI, Core, and Logging processes.

### Packages

The Week 4 code uses Java packages so that it can coexist with the old Week 1–3 source files.

```text
src/week4/
├── ui/
├── core/
├── logging/
├── ipc/
├── integration/
└── logs/
```

The original `src/` files are kept unchanged.

### Run

From the project root on Linux/WSL/Termux:

```bash
javac -d build $(find src/week4 -name "*.java")
java -cp build week4.integration.Week4Launcher
```

The launcher creates the FIFO files and starts the UI, Core, and Logging processes.

### Logging

Execution and error messages are sent:

```text
Core Process
     |
     | core_to_log.fifo
     v
Logging Process
     |
     v
src/week4/logs/simulator.log
```

### Tested

The Week 4 FIFO communication was tested with the real Week 3 Core functionality:

- LOAD
- STEP
- PUSH
- POP
- END
- Core → Logger logging

The test produced execution records in `simulator.log`.
