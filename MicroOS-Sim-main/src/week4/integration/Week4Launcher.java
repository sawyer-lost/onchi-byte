package week4.integration;

import java.io.*;
import java.util.*;
import week4.ipc.IPCHandler;

/** Starts the three Week 4 processes after creating POSIX FIFO named pipes. */
public class Week4Launcher {
    public static void main(String[] args) throws Exception {
        IPCHandler.createAll();
        String cp = "src";
        Process logger = new ProcessBuilder("java", "-cp", cp, "week4.logging.LoggingProcess").inheritIO().start();
        Process core = new ProcessBuilder("java", "-cp", cp, "week4.core.CoreProcess").inheritIO().start();
        Process ui = new ProcessBuilder("java", "-cp", cp, "week4.ui.UIProcess").inheritIO().start();
        Runtime.getRuntime().addShutdownHook(new Thread(IPCHandler::cleanup));
        ui.waitFor();
        if (core.isAlive()) core.destroy();
        if (logger.isAlive()) logger.destroy();
        IPCHandler.cleanup();
    }
}
