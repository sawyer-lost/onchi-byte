package week4.ipc;

import java.io.*;
import java.nio.file.*;

/** POSIX FIFO (named pipe) setup used by Week 4. */
public final class IPCHandler {
    public static final String UI_TO_CORE = "/tmp/microos_ui_to_core.fifo";
    public static final String CORE_TO_UI = "/tmp/microos_core_to_ui.fifo";
    public static final String CORE_TO_LOG = "/tmp/microos_core_to_log.fifo";
    private IPCHandler() { }

    public static void createFifo(String path) throws Exception {
        Files.deleteIfExists(Paths.get(path));
        Process p = new ProcessBuilder("mkfifo", path).inheritIO().start();
        if (p.waitFor() != 0) throw new IOException("mkfifo failed: " + path);
    }

    public static void createAll() throws Exception {
        createFifo(UI_TO_CORE);
        createFifo(CORE_TO_UI);
        createFifo(CORE_TO_LOG);
    }

    public static void cleanup() {
        try { Files.deleteIfExists(Paths.get(UI_TO_CORE)); } catch (Exception ignored) { }
        try { Files.deleteIfExists(Paths.get(CORE_TO_UI)); } catch (Exception ignored) { }
        try { Files.deleteIfExists(Paths.get(CORE_TO_LOG)); } catch (Exception ignored) { }
    }
}
