package week4.core;

import java.io.*;

/** Week 4 Core Process: the real Week 3 simulator running behind FIFO IPC. */
public class CoreProcess {
    public static final String UI_TO_CORE = "/tmp/microos_ui_to_core.fifo";
    public static final String CORE_TO_UI = "/tmp/microos_core_to_ui.fifo";
    public static final String CORE_TO_LOG = "/tmp/microos_core_to_log.fifo";

    public static void main(String[] args) throws Exception {
        Simulator simulator = new Simulator();
        try (BufferedReader in = new BufferedReader(new FileReader(UI_TO_CORE));
             PrintWriter uiOut = new PrintWriter(new FileWriter(CORE_TO_UI), true);
             PrintWriter logOut = new PrintWriter(new FileWriter(CORE_TO_LOG), true)) {
            uiOut.println("CORE_READY");
            logOut.println("INFO|Core process started");
            String command;
            while ((command = in.readLine()) != null) {
                if ("EXIT".equals(command)) break;
                String response = handle(command, simulator, logOut);
                uiOut.println(response.replace('\n', '|'));
            }
            logOut.println("INFO|Core process stopped");
        }
    }

    private static String handle(String command, Simulator simulator, PrintWriter log) {
        try {
            if (command.startsWith("LOAD|")) {
                String payload = command.substring(5);
                String[] program = payload.split("\\|", -1);
                simulator.loadProgram(program);
                log.println("INFO|Program loaded with " + program.length + " lines");
                return "LOAD_OK|" + program.length + " instructions";
            }
            switch (command) {
                case "STEP": {
                    Instruction i = simulator.fetch();
                    if (i == null) return "PROGRAM_FINISHED";
                    simulator.decode();
                    simulator.execute();
                    String status = simulator.getExecutionStatus();
                    if (status.startsWith("Execution error") || status.startsWith("Unsupported"))
                        log.println("ERROR|" + status);
                    else
                        log.println("INFO|Executed: " + i.getName() +
                                (i.getOperand().isEmpty() ? "" : " " + i.getOperand()));
                    return "STEP_OK|" + status + "|TRACE|" + simulator.getExecutionTrace().replace('\n', ';');
                }
                case "RUN":
                    simulator.run();
                    log.println(simulator.getExecutionStatus().startsWith("Stopped") ?
                            "ERROR|" + simulator.getExecutionStatus() : "INFO|Program run completed");
                    return "RUN_OK|" + simulator.getExecutionStatus();
                case "RESET":
                    simulator.reset();
                    log.println("INFO|Simulator reset");
                    return "RESET_OK";
                case "SHOW":
                    return "STATE|" + simulator.getCPU().getState().replace('\n', ';');
                case "HELP":
                    return "COMMANDS:LOAD,STEP,RUN,SHOW,RESET,HELP,EXIT";
                default:
                    log.println("ERROR|Unknown core command: " + command);
                    return "ERROR|Unknown command: " + command;
            }
        } catch (Exception e) {
            log.println("ERROR|" + e.getClass().getSimpleName() + ": " + e.getMessage());
            return "ERROR|" + e.getMessage();
        }
    }
}
