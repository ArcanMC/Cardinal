package fr.arcanmc.cardinal.core.console;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConsoleErrorStream extends PrintStream {
    protected final static String ERROR_RED = "\u001B[31;1m";
    protected final static String RESET_COLOR = "\u001B[0m";
    private final PrintStream logs;
    private final Console console;

    public ConsoleErrorStream(Console console, OutputStream out, PrintStream logs) {
        super(out);
        this.logs = logs;
        this.console = console;
    }

    private String getErrorTimestamp() {
        return new SimpleDateFormat("HH':'mm':'ss").format(new Date());
    }

    private void performLogging(String logMessage, Runnable superCall) {
        console.stashLine();
        String date = getErrorTimestamp();
        logs.println("[" + date + " Error] " + logMessage);
        superCall.run();
        console.unstashLine();
    }

    @Override
    public void println() {
        performLogging("", () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + RESET_COLOR));
    }

    @Override
    public void println(boolean x) {
        performLogging(Boolean.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(char x) {
        performLogging(Character.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(char[] x) {
        performLogging(String.valueOf(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + String.valueOf(x) + RESET_COLOR));
    }

    @Override
    public void println(double x) {
        performLogging(Double.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(float x) {
        performLogging(Float.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(int x) {
        performLogging(Integer.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(long x) {
        performLogging(Long.toString(x), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(Object x) {
        assert x != null;
        performLogging(x.toString(), () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error]" + x + RESET_COLOR));
    }

    @Override
    public void println(String string) {
        performLogging(string, () -> super.println(ERROR_RED + "[" + getErrorTimestamp() + " Error] " + string + RESET_COLOR));
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        console.stashLine();
        String date = getErrorTimestamp();
        logs.printf(l, "[" + date + " Error]" + format, args);
        PrintStream stream = super.printf(l, ERROR_RED + "[" + date + " Error]" + format + RESET_COLOR, args);
        console.unstashLine();
        return stream;
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        console.stashLine();
        String date = getErrorTimestamp();
        logs.printf("[" + date + " Error]" + format, args);
        PrintStream stream = super.printf(ERROR_RED + "[" + date + " Error]" + format + RESET_COLOR, args);
        console.unstashLine();
        return stream;
    }
}