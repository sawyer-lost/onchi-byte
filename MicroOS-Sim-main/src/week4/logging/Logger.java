package week4.logging;

/** Simple logging message helper used by the Week 4 logging process. */
public final class Logger {
    private Logger() { }
    public static String info(String message) { return "INFO|" + message; }
    public static String error(String message) { return "ERROR|" + message; }
}
