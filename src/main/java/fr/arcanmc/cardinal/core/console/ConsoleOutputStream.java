package fr.arcanmc.cardinal.core.console;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConsoleOutputStream extends PrintStream {
    private PrintStream logs;
    private Console console;

    public ConsoleOutputStream(Console console, OutputStream out, PrintStream logs) {
        super(out);
        this.logs = logs;
        this.console = console;
    }

    private void stashConsoleLine() {
        try {
            console.stashLine();
        } catch (Exception ignore) {
        }
    }

    private void unstashConsoleLine() {
        try {
            console.unstashLine();
        } catch (Exception ignore) {
        }
    }

    private String getFormattedDate() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    private String formatMessage(String message) {
        return "[" + getFormattedDate() + " Info] " + message;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        stashConsoleLine();
        String formattedString = formatMessage(format);
        logs.printf(l, formattedString, args);
        PrintStream stream = super.printf(l, formattedString, args);
        unstashConsoleLine();
        return stream;
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        stashConsoleLine();
        String formattedString = formatMessage(format);
        logs.printf(formattedString, args);
        PrintStream stream = super.printf(formattedString, args);
        unstashConsoleLine();
        return stream;
    }

    @Override
    public void println() {
        stashConsoleLine();
        String formattedString = formatMessage("");
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(boolean x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(char x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(char[] x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(double x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(float x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(int x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(long x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(Object x) {
        stashConsoleLine();
        String formattedString = formatMessage(String.valueOf(x));
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }

    @Override
    public void println(String string) {
        stashConsoleLine();
        String formattedString = formatMessage(string);
        logs.println(formattedString);
        super.println(formattedString);
        unstashConsoleLine();
    }
}