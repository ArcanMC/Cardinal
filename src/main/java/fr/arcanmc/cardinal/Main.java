package fr.arcanmc.cardinal;

public class Main {

    private static Cardinal cardinal;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(" ________  ________  ________  ________  ___  ________   ________  ___");
            System.out.println("|\\   ____\\|\\   __  \\|\\   __  \\|\\   ___ \\|\\  \\|\\   ___  \\|\\   __  \\|\\  \\");
            System.out.println("\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\_|\\ \\ \\  \\ \\  \\\\ \\  \\ \\  \\|\\  \\ \\  \\");
            System.out.println("\\ \\  \\    \\ \\   __  \\ \\   _  _\\\\ \\  \\ \\\\ \\ \\  \\ \\  \\\\ \\  \\ \\   __  \\ \\  \\");
            System.out.println("\\ \\  \\____\\ \\  \\ \\  \\ \\  \\\\  \\\\\\ \\  \\_\\\\ \\ \\  \\ \\  \\\\ \\  \\ \\  \\ \\  \\ \\  \\____");
            System.out.println("\\ \\_______\\ \\__\\ \\__\\ \\__\\\\ _\\\\\\ \\_______\\ \\__\\ \\__\\\\ \\__\\ \\__\\ \\__\\ \\_______\\");
            System.out.println("\\|_______|\\|__|\\|__|\\|__|\\|__|\\|_______|\\|__|\\|__| \\|__|\\|__|\\|__|\\|_______|");
            System.out.println("  ");
        });
        thread.start();
        cardinal = new Cardinal();
    }

}
