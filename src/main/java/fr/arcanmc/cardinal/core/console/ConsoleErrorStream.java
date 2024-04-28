package fr.arcanmc.cardinal.core.console;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ConsoleErrorStream extends PrintStream {
    private final static String ERROR_FORMAT = "[%s Error]";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH':'mm':'ss");
    private static final String ERROR_RED = "\u001B[31;1m";
    private static final String RESET_COLOR = "\u001B[0m";

    private final PrintStream logs;
    private final Console console;

    public ConsoleErrorStream(Console console, OutputStream out, PrintStream logs) {
        super(out);
        this.logs = logs;
        this.console = console;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        return printFormatted((fmt, arguments) -> super.printf(l, fmt, arguments), format, args);
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        return printFormatted(super::printf, format, args);
    }

    @Override
    public void println() {
        printLog(super::println, "");
    }

    @Override
    public void println(boolean x) {
        printLog(super::println, String.valueOf(x));
    }

    @Override
    public void println(char x) {
        printLog(super::println, String.valueOf(x));
    }

    @Override
    public void println(char[] x) {
        printLog(super::println, String.valueOf(x));
    }

    // Similar method overload for double, float, int, long, Object and String

    private PrintStream printFormatted(BiFunction<String, Object[], PrintStream> printOperation, String format, Object... args) {
        console.stashLine();
        String date = DATE_FORMAT.format(new Date());
        String errorFormat = String.format(ERROR_FORMAT, date);
        logs.printf(errorFormat + format, args);
        PrintStream stream = printOperation.apply(ERROR_RED + errorFormat + RESET_COLOR + format, args);
        console.unstashLine();
        return stream;
    }

    private void printLog(Consumer<String> printer, String message) {
        final String date = DATE_FORMAT.format(new Date());
        final String error = String.format(ERROR_FORMAT, date);
        logs.println(error + message);
        printer.accept(ERROR_RED + error + message + RESET_COLOR);
        console.unstashLine();
    }
}
