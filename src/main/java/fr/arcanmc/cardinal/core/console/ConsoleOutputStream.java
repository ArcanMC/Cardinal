package fr.arcanmc.cardinal.core.console;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConsoleOutputStream extends PrintStream {

    private final PrintStream logs;
    private final Console console;

    public ConsoleOutputStream(Console console, OutputStream out, PrintStream logs) {
        super(out);
        this.logs = logs;
        this.console = console;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        stashAndAppendMessage(l, format, args);
        PrintStream stream = super.printf(l, getTimeStampedMessage(format), args);
        return unstashAndReturnStream(stream);
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        stashAndAppendMessage(format, args);
        PrintStream stream = super.printf(getTimeStampedMessage(format), args);
        return unstashAndReturnStream(stream);
    }

    @Override
    public void println() {
        stashAndAppendMessage();
        super.println(getTimeStampedMessage());
        console.unstashLine();
    }

    @Override
    public void println(Object x) {
        stashAndAppendMessage(String.valueOf(x));
        super.println(getTimeStampedMessage(String.valueOf(x)));
        console.unstashLine();
    }

    private void stashAndAppendMessage(Object... args) {
        console.stashLine();
        logs.printf(getTimeStampedMessage(args));
    }

    private String getTimeStampedMessage(Object... args) {
        String date = new SimpleDateFormat("HH':'mm':'ss").format(new Date());
        return "[" + date + " Info]" + (args.length > 0 ? String.valueOf(args[0]) : "");
    }

    private PrintStream unstashAndReturnStream(PrintStream stream) {
        console.unstashLine();
        return stream;
    }
}
