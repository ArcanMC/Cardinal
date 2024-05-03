package fr.arcanmc.cardinal;

import fr.arcanmc.cardinal.core.console.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class CardinalTest {

    private Cardinal cardinal;

    @BeforeEach
    void setUp() {
        cardinal = new Cardinal();
    }

    @Test
    void testInstanceGetter() {
        Cardinal instance = Cardinal.getInstance();
        assertNotNull(instance);
        assertSame(cardinal, instance);
    }

    @Test
    void testRunningGetter() {
        AtomicBoolean running = cardinal.getRunning();
        assertNotNull(running);
        assertSame(cardinal.getRunning(), running);
    }

    @Test
    void testConsoleGetter() {
        Console console = cardinal.getConsole();
        assertNotNull(console);
    }

    @Test
    void testCardinalSchedulerGetter() {
        assertNotNull(cardinal.getCardinalScheduler());
    }

    @Test
    void testServerPropertiesGetter() {
        assertNotNull(cardinal.getServerProperties());
    }

    @Test
    void testModuleDirectoryGetter() {
        File file = cardinal.getModuleDirectory();
        assertNotNull(file);
        assertTrue(file.exists());
    }

    @Test
    void testModuleManagerGetter() {
        assertNotNull(cardinal.getModuleManager());
    }


}
