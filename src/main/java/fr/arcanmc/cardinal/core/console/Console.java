package fr.arcanmc.cardinal.core.console;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.utils.CustomStringUtils;
import jline.console.ConsoleReader;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
    private static final String DATE_FORMAT_LOGS = "yyyy'-'MM'-'dd'_'HH'-'mm'-'ss'_'zzz'.log'";
    private static final String DATE_FORMAT_MESSAGES = "HH':'mm':'ss";
    private static final String CONSOLE = "CONSOLE";
    private static final String PROMPT = "> ";
    protected static final String ERROR_RED = "\u001B[31;1m";
    protected static final String RESET_COLOR = "\u001B[0m";

    private Terminal terminal;
    private LineReader lineReader;
    private ConsoleReader reader;
    private InputStream in;
    private PrintStream out;
    private PrintStream err;
    public PrintStream logs;

    public Console(InputStream in, PrintStream out, PrintStream err) throws IOException {
        this.logs = setupLogs();
        this.in = setupInput(in);
        this.out = setupOutput(out);
        this.err = setupError(err);
        reader = new ConsoleReader(in, out);
        reader.setExpandEvents(false);
        reader.setHandleUserInterrupt(false);
        terminal = TerminalBuilder.builder().streams(in, out).system(true).jansi(true).build();
        lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        lineReader.setAutosuggestion(LineReader.SuggestionType.NONE);
    }

    private PrintStream setupLogs() throws IOException {
        String fileName = formatCurrentDate(DATE_FORMAT_LOGS);
        File dir = new File("logs");
        dir.mkdirs();
        File logsFile = new File(dir, fileName);
        return new PrintStream(logsFile);
    }

    private InputStream setupInput(InputStream in) {
        if (in != null) {
            System.setIn(in);
            return System.in;
        }
        return null;
    }

    private PrintStream setupOutput(PrintStream out) {
        System.setOut(new ConsoleOutputStream(this, out == null ? createDummyPrintStream() : out, this.logs));
        return System.out;
    }

    private PrintStream setupError(PrintStream err) {
        System.setErr(new ConsoleErrorStream(this, err == null ? createDummyPrintStream() : err, this.logs));
        return System.err;
    }

    private PrintStream createDummyPrintStream() {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                //DO NOTHING
            }
        });
    }

    public void run() {
        if (in == null) {
            return;
        }
        while (true) {
            try {
                String command = lineReader.readLine(PROMPT).trim();
                if (command.length() > 0) {
                    String[] input = CustomStringUtils.splitStringToArgs(command);
                    new Thread(() -> Cardinal.getInstance().dispatchCommand(input)).start();
                }
            } catch (UserInterruptException e) {
                System.exit(0);
            } catch (EndOfFileException e) {
                break;
            }
        }
    }

    protected void stashLine() {
        try {
            lineReader.callWidget(LineReader.CLEAR);
        } catch (Exception ignore) {
        }
    }

    protected void unstashLine() {
        try {
            lineReader.callWidget(LineReader.REDRAW_LINE);
            lineReader.callWidget(LineReader.REDISPLAY);
            lineReader.getTerminal().writer().flush();
        } catch (Exception ignore) {
        }
    }

    public void sendMessage(String message) {
        stashLine();
        String date = formatCurrentDate(DATE_FORMAT_MESSAGES);
        logs.println("[" + date + " Info] " + message);
        try {
            reader.getOutput().append("[").append(date).append(" Info] ").append(message).append("\n");
            reader.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        unstashLine();
    }

    private String formatCurrentDate(String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(new Date());
    }
}
