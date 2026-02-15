package it.unibo.goldhunt;

/**
 * JVM entry point for the Gold Hunt application.
 *
 * <p>
 * This class is responsible only for delegating the startup process
 * to {@link GoldHuntApp}, keeping the application bootstrap logic
 * separated from the standard {@code main} method.
 */
public final class Launcher {

    /**
     * Utility class contructor.
     * 
     * <p>
     * This constructor is private to prevent instantiation.
     */
    private Launcher() { }

    /**
     * Starts the application.
     * 
     * @param args command-line arguments (currently unused)
     */
    public static void main(final String[] args) {
        GoldHuntApp.start();
    }
}
