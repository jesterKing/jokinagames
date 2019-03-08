package net.jokinagames;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintWriter;

public class Util {
    public enum Color {
        RESET("\033[0m"),

        //tavalliset
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m"),    // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"),   // BLACK
        RED_BOLD("\033[1;31m"),     // RED
        GREEN_BOLD("\033[1;32m"),   // GREEN
        YELLOW_BOLD("\033[1;33m"),  // YELLOW
        BLUE_BOLD("\033[1;34m"),    // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"),    // CYAN
        WHITE_BOLD("\033[1;37m"),   // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),     // BLACK
        RED_UNDERLINED("\033[4;31m"),       // RED
        GREEN_UNDERLINED("\033[4;32m"),     // GREEN
        YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
        BLUE_UNDERLINED("\033[4;34m"),      // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"),      // CYAN
        WHITE_UNDERLINED("\033[4;37m"),     // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"),   // BLACK
        RED_BACKGROUND("\033[41m"),     // RED
        GREEN_BACKGROUND("\033[42m"),   // GREEN
        YELLOW_BACKGROUND("\033[43m"),  // YELLOW
        BLUE_BACKGROUND("\033[44m"),    // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"),    // CYAN
        WHITE_BACKGROUND("\033[47m"),   // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),     // BLACK
        RED_BRIGHT("\033[0;91m"),       // RED
        GREEN_BRIGHT("\033[0;92m"),     // GREEN
        YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
        BLUE_BRIGHT("\033[0;94m"),      // BLUE
        MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
        CYAN_BRIGHT("\033[0;96m"),      // CYAN
        WHITE_BRIGHT("\033[0;97m"),     // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"),      // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    //private final static PrintWriter pw = new PrintWriter(System.out, true);

    public static void alusta() {
        AnsiConsole.systemInstall();
    }


    /**
     * Tulosta UTF-8 merkkijono
     * @param string
     */
    public static void println(String string) {
        AnsiConsole.out.println(Color.BLACK_BACKGROUND_BRIGHT + string + Color.RESET);
    }

    private static boolean flip = false;
    public static void print(String string) {
        if(flip) {
            AnsiConsole.out.print(/*Color.RESET + "" +  */Color.RED_BACKGROUND + /*"" + Color.WHITE_BRIGHT +*/ string /* + Color.RESET*/);
        }
        else {
            AnsiConsole.out.print(/*Color.RESET + "" +  */Color.GREEN_BACKGROUND + /*"" + Color.WHITE_BRIGHT +*/ string /* + Color.RESET*/);
        }
        flip = !flip;
    }

    public static void print(String string, Color fg) {
        Color bg = flip ? Color.RED_BACKGROUND : Color.GREEN_BACKGROUND;
        print(string, fg, bg);
        flip = !flip;
    }
    public static void print(String string, Color fg, Color bg) {
        AnsiConsole.out.print(fg);
        AnsiConsole.out.print(bg);
        AnsiConsole.out.print(string);
    }

    public static void ln() {
        AnsiConsole.out.println(Color.RESET);
        flip = !flip;
    }

    public static char charFromInt(int nr) {
        return (char)nr;
    }
}
