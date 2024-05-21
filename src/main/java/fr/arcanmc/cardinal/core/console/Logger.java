package fr.arcanmc.cardinal.core.console;

import fr.arcanmc.cardinal.Cardinal;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Logger {

    private final ConsoleOutputStream infoStream;
    private final ConsoleErrorStream errorStream;

    public Logger(InputStream in, PrintStream out, PrintStream err) throws IOException {
        Console console = Cardinal.getInstance().getConsole();
        infoStream = new ConsoleOutputStream(console, out, console.logs);
        errorStream = new ConsoleErrorStream(console, err, console.logs);
    }

    public void info(String message) {
        infoStream.println(message);
    }

    public void error(String message) {
        errorStream.println(message);
    }

    public void error(String message, Throwable throwable) {
        errorStream.println(message);
        throwable.printStackTrace(errorStream);
    }

    public void error(Throwable throwable) {
        throwable.printStackTrace(errorStream);
    }

    public void warn(String message) {
        errorStream.println(message);
    }

    public void warn(String message, Throwable throwable) {
        errorStream.println(message);
        throwable.printStackTrace(errorStream);
    }

    public void warn(Throwable throwable) {
        throwable.printStackTrace(errorStream);
    }

    public void debug(String message) {
        infoStream.println(message);
    }

}
