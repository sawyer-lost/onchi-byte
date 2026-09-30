package week4.logging;

/** Data object representing a message sent to the Logging Process. */
public class LogMessage {
    private final String level;
    private final String message;
    public LogMessage(String level, String message) { this.level = level; this.message = message; }
    public String toPipeMessage() { return level + "|" + message; }
}
