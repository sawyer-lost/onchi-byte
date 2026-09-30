package week4.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/** Week 4 UI Process. Communicates with the Core Process only through FIFOs. */
public class UIProcess {
    private static final String UI_TO_CORE = "/tmp/microos_ui_to_core.fifo";
    private static final String CORE_TO_UI = "/tmp/microos_core_to_ui.fifo";
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea program;
    private JTextArea output;

    public static void main(String[] args) throws Exception { SwingUtilities.invokeLater(() -> new UIProcess().start()); }

    private void start() {
        try {
            out = new PrintWriter(new FileWriter(UI_TO_CORE), true);
            in = new BufferedReader(new FileReader(CORE_TO_UI));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Start Week4Launcher first.\n" + e.getMessage());
            return;
        }
        JFrame frame = new JFrame("MicroOS Simulator — Week 4 UI Process");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 600);
        program = new JTextArea("MOV A,#10\nMOV R0,#20\nADD A,R0\nPUSH A\nPOP R1\nENQUEUE #10\nENQUEUE #20\nDEQUEUE R2\nEND");
        output = new JTextArea(); output.setEditable(false);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton(buttons, "LOAD", e -> load());
        addButton(buttons, "STEP", e -> send("STEP"));
        addButton(buttons, "RUN", e -> send("RUN"));
        addButton(buttons, "SHOW", e -> send("SHOW"));
        addButton(buttons, "RESET", e -> send("RESET"));
        addButton(buttons, "HELP", e -> send("HELP"));
        addButton(buttons, "EXIT", e -> { send("EXIT"); frame.dispose(); });
        frame.add(buttons, BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(program), new JScrollPane(output));
        split.setDividerLocation(260);
        frame.add(split, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); frame.setVisible(true);
        new Thread(this::receive, "week4-ui-receiver").start();
    }

    private void addButton(JPanel p, String text, java.awt.event.ActionListener l) { JButton b = new JButton(text); b.addActionListener(l); p.add(b); }
    private void load() {
        String[] lines = program.getText().replace("\r", "").split("\n");
        StringBuilder msg = new StringBuilder("LOAD|");
        for (int i = 0; i < lines.length; i++) { if (i > 0) msg.append('|'); msg.append(lines[i].trim()); }
        send(msg.toString());
    }
    private void send(String command) { if (out != null) out.println(command); }
    private void receive() {
        try { String line; while ((line = in.readLine()) != null) { final String received = line; SwingUtilities.invokeLater(() -> output.append(received + "\n")); } }
        catch (Exception e) { SwingUtilities.invokeLater(() -> output.append("UI IPC closed: " + e.getMessage() + "\n")); }
    }
}
