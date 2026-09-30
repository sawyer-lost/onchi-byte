package week4.logging;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

/** Separate Week 4 Logging Process. Receives messages from Core through a POSIX FIFO. */
public class LoggingProcess {
    private static final String CORE_TO_LOG = "/tmp/microos_core_to_log.fifo";
    private static final String LOG_DIR = "src/week4/logs";
    private static final String LOG_FILE = LOG_DIR + "/simulator.log";

    public static void main(String[] args) throws Exception {
        Files.createDirectories(Paths.get(LOG_DIR));
        try (BufferedReader in = new BufferedReader(new FileReader(CORE_TO_LOG));
             PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println("===== Logging Process Started =====");
            String message;
            while ((message = in.readLine()) != null) {
                out.println("[" + LocalDateTime.now() + "] " + message);
                out.flush();
                if (message.equals("INFO|Core process stopped")) break;
            }
            out.println("===== Logging Process Stopped =====");
        }
    }
}
